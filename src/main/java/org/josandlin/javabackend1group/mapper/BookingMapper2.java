package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dao.BookedObjectDao;
import org.josandlin.javabackend1group.dao.BookingDao;
import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper2 {

//    BookedObjectDao bookedObjectDao;
//    BookedObjectMapper2 bookedObjectMapper2;
//
//    @Autowired
//    public BookingMapper2(BookedObjectDao bookedObjectDao, BookedObjectMapper2 bookedObjectMapper2){
//        this.bookedObjectDao = bookedObjectDao;
//        this.bookedObjectMapper2 = bookedObjectMapper2;
//    }
//
//    public BookingDetailedDTO toDTO(Booking booking){
//        List<BookedObjectSmallDTO> bookedObjects = bookedObjectDao.getBookedObjectByBookingId(booking.getId()).stream().map(bookedObjectMapper2::toDTO).collect(Collectors.toList());
//        return new BookingDetailedDTO(booking.getId(), bookedObjects);
//    }
}
