package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface BookingService {

    // Metoder som ska vara i customer service

    CustomerDTO findCustomerById(Long id);

    List<CustomerDTO> getAllCustomers();


    //

    List<BookingDTO> getBookingsByCustomerId(Long customerId);

    BookingDTO getBookingById(Long id);

    @Transactional
    BookingDTO createBooking(Long customerId);

    @Transactional
    void deleteBookedObject(Long bookedObjectId);

    List<BookingDTO> getAllBookings();

    List<BookedObjectDTO> getBookedRoomsByBookingId(Long bookingId);

    @Transactional
    BookedObjectDTO saveBookedObject(RoomDTO room, Long bookingId, LocalDate startDate, LocalDate endDate);

    CustomerDTO getCustomerByBookingId(Long id);

    BookedObjectDTO getBookedObjectById(Long id);

    @Transactional
    void deleteExtraFromBookedObjectById(Long extraId);

    List<ExtraTypeDTO> getExtraOptionsAvailable(Long bookedObjectId);

    @Transactional
    BookedObjectDTO addExtraToBookedObject(Long bookedObjectId, Long extraTypeId);

    @Transactional
    BookedObjectDTO editBookedObject(Long bookedObjectId, Long roomId, LocalDate startDate, LocalDate endDate);

//    @Transactional
//    BookedObject addBookedObjectToBooking(BookedObject bookedObject, Long bookingId);
//
//    @Transactional
//    void removeBookedObject(Long bookedObjectId);
//
//    @Transactional
//    BookedObject editBookedObject(BookedObject bookedObject);
//
//    List<Room> getRoomsByBookingId(Long bookingId);

}
