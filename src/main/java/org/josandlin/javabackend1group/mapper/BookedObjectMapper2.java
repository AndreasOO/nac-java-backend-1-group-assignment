package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.AddedExtraDTO;
import org.josandlin.javabackend1group.dto.BookedObjectSmallDTO;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookedObjectMapper2 {

//    RoomMapper roomMapper;
//    AddedExtraMapper addedExtraMapper;
//
//    public BookedObjectMapper2(RoomMapper roomMapper, AddedExtraMapper addedExtraMapper){
//        this.roomMapper=roomMapper;
//    }
//
//    public BookedObjectSmallDTO toDTO(BookedObject bookedObject){
//        RoomDTO roomDTO = roomMapper.toDTO(bookedObject.getRoom());
//        List<AddedExtraDTO> extras = bookedObject.getExtras().stream().map(addedExtraMapper::toDTO).toList();
//
//        return new BookedObjectSmallDTO(bookedObject.getId(), roomDTO, extras, bookedObject.getStartDate(), bookedObject.getEndDate());
//    }
}
