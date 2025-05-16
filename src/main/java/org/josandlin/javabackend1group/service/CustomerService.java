package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface CustomerService {
    List<Room> getRoomsByBookingId(Long bookingId);

    List<Room> getAvailableRoomsBetweenDates(Date startDate, Date endDate);

    @Transactional
    BookedObject addBookedObjectToBooking(BookedObject bookedObject, Long bookingId);

    @Transactional
    void removeBookedObject(Long bookedObjectId);

    @Transactional
    BookedObject editBookedObject(BookedObject bookedObject);
}
