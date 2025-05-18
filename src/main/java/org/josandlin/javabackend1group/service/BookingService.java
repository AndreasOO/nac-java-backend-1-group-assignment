package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.cglib.core.Local;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface BookingService {
    List<Booking> getBookingsByCustomerId(Long customerId);

    Booking getBookingById(Long id);

    @Transactional
    Booking createBooking(Booking booking);

    @Transactional
    BookedObject addBookedObjectToBooking(BookedObject bookedObject, Long bookingId);

    @Transactional
    void removeBookedObject(Long bookedObjectId);

    @Transactional
    BookedObject editBookedObject(BookedObject bookedObject);

    List<Room> getRoomsByBookingId(Long bookingId);

    List<Room> getAvailableRoomsBetweenDatesAndWithinMaxCapacity(LocalDate startDate, LocalDate endDate, int numOfResidents);

    List<Booking> getAllBookings();

    List<BookedObject> getBookedRoomsByBookingId(Long bookingId);

    List<Room> getAllRooms();

    int getRoomMaxCapacity();

    Set<Room> getBookedRoomsBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Room> getAvailableRoomsWithinMaxCapacity(LocalDate startDate, LocalDate endDate, int quests);

    Room getRoomById(Long id);

    void saveBookedObject(BookedObject bookedObject);
    // lägg till metod som sparar bookedobject

    Customer getCustomerByBookingId(Long id);
}
