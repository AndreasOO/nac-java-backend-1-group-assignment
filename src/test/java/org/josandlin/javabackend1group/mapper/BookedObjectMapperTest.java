package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.AddedExtraDTO;
import org.josandlin.javabackend1group.dto.ExtraTypeDTO;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.ExtraType;
import org.junit.jupiter.api.Test;

class BookedObjectMapperTest {

    BookedObjectMapper bookedObjectMapper = new BookedObjectMapper(new RoomMapper(new RoomTypeMapper()),
                                                                   new AddedExtraMapper(new ExtraTypeMapper()),
                                                                   new BookingMapper(new CustomerMapper()));


    ExtraType extraBedEntity = new ExtraType(2L,"bedFromEntity",200);
    AddedExtra addedExtraEntity1 = new AddedExtra(1L, extraBedEntity);
    AddedExtra addedExtraEntity2 = new AddedExtra(3L, extraBedEntity);


    ExtraTypeDTO extraBedDto = new ExtraTypeDTO(4L,"bedFromDto",200);
    AddedExtraDTO addedExtraDto1 = new AddedExtraDTO(5L,extraBedDto);
    AddedExtraDTO addedExtraDto2 = new AddedExtraDTO(6L,extraBedDto);


    @Test
    void testEntityToDto() {

    }

    @Test
    void testDtoToEntity() {


    }

}