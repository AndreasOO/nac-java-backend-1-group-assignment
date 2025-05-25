package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.josandlin.javabackend1group.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookedObjectDao bookedObjectDao;
    private final AddedExtraDao addedExtraDao;
    private final ExtraTypeDao extraTypeDao;
    private final CustomerDao customerDao;

    private final BookingMapper bookingMapper;
    private final CustomerMapper customerMapper;
    private final RoomMapper roomMapper;
    private final BookedObjectMapper bookedObjectMapper;
    private final ExtraTypeMapper extraTypeMapper;

    private final RoomService roomService;

    private final CustomerService customerService;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao,
                              BookedObjectDao bookedObjectDao,
                              AddedExtraDao addedExtraDao,
                              ExtraTypeDao extraTypeDao,
                              CustomerDao customerDao,
                              BookingMapper bookingMapper,
                              CustomerMapper customerMapper,
                              RoomMapper roomMapper,
                              BookedObjectMapper bookedObjectMapper,
                              ExtraTypeMapper extraTypeMapper, RoomService roomService,
                              CustomerService customerService) {

        this.bookingDao = bookingDao;
        this.bookedObjectDao = bookedObjectDao;
        this.addedExtraDao = addedExtraDao;
        this.extraTypeDao = extraTypeDao;
        this.customerDao = customerDao;

        this.bookingMapper = bookingMapper;
        this.customerMapper = customerMapper;
        this.roomMapper = roomMapper;
        this.bookedObjectMapper = bookedObjectMapper;
        this.extraTypeMapper = extraTypeMapper;
        this.roomService = roomService;

        this.customerService = customerService;
    }


    @Override
    public CustomerDTO findCustomerById(Long id){
        return customerMapper.toDTO(customerDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found")));
    }

    /// Stämmer denna tro?
//    @Override
//    public Customer findCustomerById(Long id){
//        return customerDao.findById(id).stream().map(customerMapper::toDTO).filter(customer -> customer.getId().equals(id));
//    }


    @Override
    public List<CustomerDTO> getAllCustomers(){
        return customerDao.findAll().stream().map(customerMapper::toDTO).toList();
    }

    @Override
    public List<BookingDTO> getBookingsByCustomerId(Long customerId) {
        return bookingDao.findAll().stream().map(bookingMapper::toDTO).filter(booking -> booking.getCustomer().getId().equals(customerId)).toList();
    }


    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingMapper.toDTO(bookingDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found")));
    }

    @Transactional
    @Override
    public BookingDTO createBooking(Long customerId) {
        Customer customer = customerMapper.toEntity(findCustomerById(customerId));
        Booking newBooking = new Booking(customer);
        bookingDao.save(newBooking);
        return bookingMapper.toDTO(newBooking);
    }

    @Transactional
    @Override
    public void deleteBookedObject(Long bookedObjectId) {
        bookedObjectDao.deleteById(bookedObjectId);
        //TODO implement return Optional.Success / Fail?
    }

    @Override
    public List<BookedObjectDTO> getBookedRoomsByBookingId(Long bookingId){
        return bookedObjectDao.findAll().stream().filter(booking -> booking.getBooking().getId().equals(bookingId)).map(bookedObjectMapper::toDTO).toList();
    }

    @Override
    public List<BookingDTO> getAllBookings(){
        return bookingDao.findAll().stream().map(bookingMapper::toDTO).toList();
    }



//    Du kan redan använda dig av getAllAvailableRooms för du har ju redan datumen som input.
//
//    Sedan filtrera på valt rum.
//
//    Om rummet finns, gå vidare, om inte, kasta IllegalArgumentException med texten "Room is not available during input dates"

    @Transactional
    @Override
    public BookedObjectDTO saveBookedObject(RoomDTO room, Long bookingId, LocalDate startDate, LocalDate endDate){

        boolean roomIsUnavailable = bookedObjectDao.findAll()
                .stream().filter(bookedObject -> bookedObject.getStartDate().isEqual(startDate) ||
                                            (bookedObject.getStartDate().isAfter(startDate) && bookedObject.getStartDate().isBefore(endDate)) ||
                                            (bookedObject.getStartDate().isBefore(startDate) && bookedObject.getEndDate().isAfter(startDate)))
                         .map(BookedObject::getRoom)
                         .anyMatch(bookedRoom -> bookedRoom.getId().equals(room.getId()));

        if (roomIsUnavailable) {
            throw new IllegalArgumentException("Room is not available during input dates");
        }

        Booking currentBooking = bookingMapper.toEntity(getBookingById(bookingId));
        BookedObject bookedObject = new BookedObject(roomMapper.toEntity(room), List.of(), currentBooking, startDate, endDate);
        return bookedObjectMapper.toDTO(bookedObjectDao.save(bookedObject));
    }

    @Override
    public CustomerDTO getCustomerByBookingId(Long id){
        return bookingDao.findById(id).stream().map(Booking::getCustomer)
                .map(customerMapper::toDTO).findFirst().orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Override
    public BookedObjectDTO getBookedObjectById(Long id){
        return bookedObjectMapper.toDTO(bookedObjectDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Booked object not found")));
    }


    @Transactional
    @Override
    public void deleteExtraFromBookedObjectById(Long extraId){
        try{
            addedExtraDao.deleteById(extraId);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Extra not found");
        }
    }

    @Override
    public List<ExtraTypeDTO> getExtraOptionsAvailable(Long bookedObjectId){

        BookedObjectDTO bookedObject = getBookedObjectById(bookedObjectId);
        long currentExtraBedsAdded = bookedObject.getExtras().stream().filter(extra -> extra.getExtraType().getName().equals("bed")).count();

        if(bookedObject.getRoom().getRoomType().getName().equals("Single room") || currentExtraBedsAdded >= bookedObject.getRoom().getExtraBedsAvailable()){
            return extraTypeDao.findAll().stream().filter(extraType -> !extraType.getName().equals("bed")).map(extraTypeMapper::toDTO).toList();
        }
        return extraTypeDao.findAll().stream().map(extraTypeMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public BookedObjectDTO addExtraToBookedObject(Long bookedObjectId, Long extraTypeId){

        BookedObject bookedObject = bookedObjectDao.findById(bookedObjectId).orElse(null);

        if (bookedObject == null) {
            throw new IllegalArgumentException("Bed can't be added to this room");
        }

        ExtraType chosenExtraType = extraTypeDao.findById(extraTypeId).orElse(null);
        long extraBedsAlreadyAdded = bookedObject.getExtras().stream().filter(extra -> extra.getExtraType().getName().equals("bed")).count();

        if(chosenExtraType == null || chosenExtraType.getName().equals("bed")) {
            if (bookedObject.getRoom().getRoomType().getName().equals("Single room") || extraBedsAlreadyAdded >= bookedObject.getRoom().getExtraBedsAvailable()) {
                throw new IllegalArgumentException("Bed can't be added to this room");
            }
        }
        AddedExtra addedExtra = new AddedExtra(chosenExtraType);
        bookedObject.getExtras().add(addedExtra);
        return bookedObjectMapper.toDTO(bookedObjectDao.save(bookedObject));
    }


    @Transactional
    @Override
    public BookedObjectDTO editBookedObject(Long bookedObjectId, Long roomId, LocalDate startDate, LocalDate endDate){
        BookedObject bookedObject = bookedObjectDao.findById(bookedObjectId).orElseThrow(() -> new IllegalArgumentException("Booked object not found"));

        bookedObject.setRoom(roomMapper.toEntity(roomService.getRoomById(roomId)));
        bookedObject.setStartDate(startDate);
        bookedObject.setEndDate(endDate);
        bookedObject.getExtras().forEach(extra -> deleteExtraFromBookedObjectById(extra.getId()));

        return bookedObjectMapper.toDTO(bookedObjectDao.save(bookedObject));
    }



    // Metoder som inte används

//    @Override
//    public BookedObject editBookedObject(BookedObject bookedObject) {
//        return bookedObjectDao.save(bookedObject);
//    }
//
//    @Override
//    public List<Room> getRoomsByBookingId(Long bookingId) {
//        return bookedObjectDao.findAll().stream().filter(booking -> booking.getBooking().getId().equals(bookingId))
//                .map(BookedObject::getRoom)
//                .toList();
//    }
//
//    @Override
//    public BookedObject addBookedObjectToBooking(BookedObject bookedObject, Long bookingId) {
//        Booking booking = bookingDao.findById(bookingId).orElse(null);
//        if (booking == null) {
//            //TODO implement return Optional.None?
//            throw new IllegalArgumentException("Booking not found");
//        }
//        bookedObject.setBooking(booking);
//        //TODO implement return Optional.BookedObject?
//        return bookedObjectDao.save(bookedObject);
//    }

//    @Override
//    public List<Room> getAvailableRoomsBetweenDatesWithinMaxCapacity(LocalDate startDate, LocalDate endDate, int numOfResidents) {
//        return bookedObjectDao.findAll().stream().filter(bookedObj -> bookedObj.getStartDate().isAfter(endDate)
//                        || bookedObj.getEndDate().isBefore(startDate))
//                .map(BookedObject::getRoom)
//                .filter(room -> room.getMaxCapacity() >= numOfResidents)
//                .toList();
//    }

    //    @Override
//    public List<Booking> getBookingsByCustomerId(Long customerId) {
//        return bookingDao.findAll().stream().filter(booking -> booking.getCustomer().getId().equals(customerId)).toList();
//    }

//    @Override
//    public Booking getBookingById(Long id) {
//        return bookingDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
//    }

//    @Override
//    public Booking createBooking(Booking booking) {
//        return bookingDao.save(booking);
//    }


}
