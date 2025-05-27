package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.BookedObjectDTO;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookedObjectMapper {

    private final RoomMapper roomMapper;
    private final AddedExtraMapper addedExtraMapper;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookedObjectMapper(RoomMapper roomMapper, AddedExtraMapper addedExtraMapper, BookingMapper bookingMapper) {
        this.roomMapper = roomMapper;
        this.addedExtraMapper = addedExtraMapper;
        this.bookingMapper = bookingMapper;
    }

    public BookedObjectDTO toDTO(BookedObject entity) {
        if (entity == null) {
            return null;
        }

        return new BookedObjectDTO(entity.getId(),
                                    roomMapper.toDTO(entity.getRoom()),
                                    entity.getExtras().stream().map(addedExtraMapper::toDTO).toList(),
                                    bookingMapper.toDTO(entity.getBooking()),
                                    entity.getStartDate(), entity.getEndDate());
    }

    public BookedObject toEntity(BookedObjectDTO dto) {
        if (dto == null) {
            return null;
        }

        return new BookedObject(dto.getId(),
                roomMapper.toEntity(dto.getRoom()),
                dto.getExtras().stream().map(addedExtraMapper::toEntity).toList(),
                bookingMapper.toEntity(dto.getBooking()),
                dto.getStartDate(), dto.getEndDate());
    }

}
