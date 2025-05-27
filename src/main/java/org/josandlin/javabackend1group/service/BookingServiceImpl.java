package org.josandlin.javabackend1group.service;

import jakarta.validation.Valid;
import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.josandlin.javabackend1group.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.*;

@Validated
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookedObjectDao bookedObjectDao;
    private final AddedExtraDao addedExtraDao;
    private final ExtraTypeDao extraTypeDao;
    private final CustomerDao customerDao;
    private final RoomDao roomDao;

    private final BookingMapper bookingMapper;
    private final CustomerMapper customerMapper;
    private final RoomMapper roomMapper;
    private final BookedObjectMapper bookedObjectMapper;
    private final ExtraTypeMapper extraTypeMapper;

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
                              ExtraTypeMapper extraTypeMapper, RoomDao roomDao) {

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
        this.roomDao = roomDao;
    }

    @Override
    public List<BookingDTO> getBookingsByCustomerId(Long customerId) {
        return bookingDao.findAll().stream().map(bookingMapper::toDTO).filter(booking -> booking.getCustomer().getId().equals(customerId)).toList();
    }


    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingMapper.toDTO(bookingDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found")));
    }

    @Transactional
    @Override
    public BookingDTO createBooking(Long customerId) {
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Booking newBooking = new Booking(customer);
        bookingDao.save(newBooking);
        return bookingMapper.toDTO(newBooking);
    }

    @Transactional
    @Override
    public boolean deleteBookedObject(Long bookedObjectId) {
        if(!bookedObjectDao.existsById(bookedObjectId)) {
            return false;
        }
        bookedObjectDao.deleteById(bookedObjectId);
        return true;
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
    public BookedObjectDTO saveBookedObject(@Valid RoomDTO room, Long bookingId, LocalDate startDate, LocalDate endDate){

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
    public boolean deleteExtraFromBookedObjectById(Long extraId){
        if(!addedExtraDao.existsById(extraId)) {
            return false;
        }
        addedExtraDao.deleteById(extraId);
        return true;
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

        BookedObject bookedObject = bookedObjectDao.findById(bookedObjectId).orElseThrow(()-> new IllegalArgumentException("Booked object not found"));
        ExtraType chosenExtraType = extraTypeDao.findById(extraTypeId).orElseThrow(() -> new IllegalArgumentException("ExtraType not found"));

        long extraBedsAlreadyAdded = bookedObject.getExtras().stream().filter(extra -> extra.getExtraType().getName().equals("bed")).count();

        if(chosenExtraType.getName().equals("bed")) {
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

        bookedObject.setRoom(roomDao.findById(roomId).orElseThrow(()-> new IllegalArgumentException("Room not found")));
        bookedObject.setStartDate(startDate);
        bookedObject.setEndDate(endDate);
        bookedObject.getExtras().forEach(extra -> deleteExtraFromBookedObjectById(extra.getId()));

        return bookedObjectMapper.toDTO(bookedObjectDao.save(bookedObject));
    }


}
