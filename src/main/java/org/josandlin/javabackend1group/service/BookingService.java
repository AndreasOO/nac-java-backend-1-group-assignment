package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface BookingService {
    List<Booking> getBookingsByCustomerId(Long customerId);

    Optional<Booking> getBookingById(Long id);

    @Transactional
    Booking createBooking(Booking booking);

    @Transactional
    BookedObject addBookedObjectToBooking(BookedObject bookedObject, Long bookingId);

    @Transactional
    void removeBookedObject(Long bookedObjectId);

    @Transactional
    BookedObject editBookedObject(BookedObject bookedObject);

    List<Room> getRoomsByBookingId(Long bookingId);

    List<Room> getAvailableRoomsBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Booking> getAllBookings();

    List<BookedObject> getBookedRoomsByBookingId(Long bookingId);

    List<Room> getAllRooms();

}
