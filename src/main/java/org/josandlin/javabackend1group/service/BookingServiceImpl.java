package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookedObjectDao bookedObjectDao;
    private final AddedExtraDao addedExtraDao;
    private final RoomDao roomDao;
    private final RoomTypeDao roomTypeDao;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao,
                              BookedObjectDao bookedObjectDao,
                              AddedExtraDao addedExtraDao,
                              RoomDao roomDao,
                              RoomTypeDao roomTypeDao) {

        this.bookingDao = bookingDao;
        this.bookedObjectDao = bookedObjectDao;
        this.addedExtraDao = addedExtraDao;
        this.roomDao = roomDao;
        this.roomTypeDao = roomTypeDao;
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingDao.findAll().stream().filter(booking -> booking.getCustomer().getId().equals(customerId)).toList();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Override
    public Booking createBooking(Booking booking) {
        return bookingDao.save(booking);
    }


    @Override
    public BookedObject addBookedObjectToBooking(BookedObject bookedObject, Long bookingId) {
        Booking booking = bookingDao.findById(bookingId).orElse(null);
        if (booking == null) {
            //TODO implement return Optional.None?
            throw new IllegalArgumentException("Booking not found");
        }
        bookedObject.setBooking(booking);
        //TODO implement return Optional.BookedObject?
        return bookedObjectDao.save(bookedObject);
    }

    @Override
    public void removeBookedObject(Long bookedObjectId) {
        bookedObjectDao.deleteById(bookedObjectId);
        //TODO implement return Optional.Success / Fail?
    }

    @Override
    public BookedObject editBookedObject(BookedObject bookedObject) {
        return bookedObjectDao.save(bookedObject);
    }

    @Override
    public List<Room> getRoomsByBookingId(Long bookingId) {
        return bookedObjectDao.findAll().stream().filter(booking -> booking.getBooking().getId().equals(bookingId))
                                                 .map(BookedObject::getRoom)
                                                 .toList();
    }

    public List<BookedObject> getBookedRoomsByBookingId(Long bookingId){
        return bookedObjectDao.findAll().stream().filter(booking -> booking.getBooking().getId().equals(bookingId)).toList();
    }

    @Override
    public List<Room> getAvailableRoomsBetweenDatesAndWithinMaxCapacity(LocalDate startDate, LocalDate endDate, int numOfResidents) {
        return bookedObjectDao.findAll().stream().filter(bookedObj -> bookedObj.getStartDate().isAfter(endDate)
                                                                               || bookedObj.getEndDate().isBefore(startDate))
                                                 .map(BookedObject::getRoom)
                                                 .filter(room -> room.getMaxCapacity() >= numOfResidents)
                                                 .toList();
    }

    @Override
    public List<Booking> getAllBookings(){
        return bookingDao.findAll();
    }

    @Override
    public List<Room> getAllRooms(){
        return roomDao.findAll();
    }

    @Override
    public int getRoomMaxCapacity(){
        return roomDao.findAll().stream().map(Room::getMaxCapacity).max(Comparator.naturalOrder()).orElse(1);
    }

    @Override
    public Set<Room> getBookedRoomsBetweenDates(LocalDate startDate, LocalDate endDate){
        return bookedObjectDao.findAll()
                .stream().filter(bookedRoom -> !bookedRoom.getEndDate().isBefore(startDate)
                        && !bookedRoom.getStartDate().isAfter(endDate))
                .map(BookedObject::getRoom).collect(Collectors.toSet());
    }

    @Override
    public List<Room> getAvailableRoomsWithinMaxCapacity(LocalDate startDate, LocalDate endDate, int quests){
        Set<Room> bookedRooms = getBookedRoomsBetweenDates(startDate, endDate);
        return roomDao.findAll().stream()
                .filter(room -> !bookedRooms.contains(room)).filter(room -> room.getMaxCapacity() >= quests).toList();
    }

    @Override
    public Room getRoomById(Long id){
        return roomDao.findRoomById(id);
    }

    @Override
    public void saveBookedObject(BookedObject bookedObject){
        bookedObjectDao.save(bookedObject);
    }

    @Override
    public Customer getCustomerByBookingId(Long id){
        return bookingDao.findById(id).stream().map(Booking::getCustomer).findFirst().orElse(null);
    }


}
