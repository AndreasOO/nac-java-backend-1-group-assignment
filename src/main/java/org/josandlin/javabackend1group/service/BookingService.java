package org.josandlin.javabackend1group.service;

import jakarta.validation.Valid;
import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface BookingService {

    List<BookingDTO> getBookingsByCustomerId(Long customerId);

    BookingDTO getBookingById(Long id);

    @Transactional
    BookingDTO createBooking(Long customerId);

    @Transactional
    boolean deleteBookedObject(Long bookedObjectId);

    List<BookingDTO> getAllBookings();

    List<BookedObjectDTO> getBookedRoomsByBookingId(Long bookingId);

    @Transactional
    BookedObjectDTO saveBookedObject(@Valid RoomDTO room, Long bookingId, LocalDate startDate, LocalDate endDate);

    CustomerDTO getCustomerByBookingId(Long id);

    BookedObjectDTO getBookedObjectById(Long id);

    @Transactional
    boolean deleteExtraFromBookedObjectById(Long extraId);

    List<ExtraTypeDTO> getExtraOptionsAvailable(Long bookedObjectId);

    @Transactional
    BookedObjectDTO addExtraToBookedObject(Long bookedObjectId, Long extraTypeId);

    @Transactional
    BookedObjectDTO editBookedObject(Long bookedObjectId, Long roomId, LocalDate startDate, LocalDate endDate);

}
