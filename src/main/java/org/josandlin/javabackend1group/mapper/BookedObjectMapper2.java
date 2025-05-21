package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dao.BookingDao;
import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookedObjectMapper2 {

    RoomMapper2 roomMapper;
    AddedExtraMapper2 addedExtraMapper;
    BookingDao bookingDao;

    @Autowired
    public BookedObjectMapper2(RoomMapper2 roomMapper, AddedExtraMapper2 addedExtraMapper, BookingDao bookingDao) {
        this.roomMapper = roomMapper;
        this.addedExtraMapper = addedExtraMapper;
        this.bookingDao = bookingDao;
    }

    public BookedObjectDetailedDTO toDTO(BookedObject entity) {
        if (entity == null) {
            return null;
        }
        return new BookedObjectDetailedDTO(entity.getId(),
                                            roomMapper.toDTO(entity.getRoom()),
                                            entity.getExtras().stream().map(addedExtraMapper::toDTO).collect(Collectors.toList()),
                                            entity.getBooking().getId(),
                                            entity.getStartDate(),
                                            entity.getEndDate());
    }

    public BookedObject toEntity(BookedObjectDetailedDTO dto) {
        if (dto == null) {
            return null;
        }
        return new BookedObject(dto.getBookedObjectId(),
                                roomMapper.toEntity(dto.getRoom()),
                                dto.getExtras().stream().map(addedExtraMapper::toEntity).collect(Collectors.toList()),
                                bookingDao.findById(dto.getBookingId()).orElseThrow(()-> new IllegalArgumentException("Booking not found")),
                                dto.getStartDate(),
                                dto.getEndDate());
    }

}
