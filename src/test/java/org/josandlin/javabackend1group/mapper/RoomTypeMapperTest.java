package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.dto.RoomTypeDTO;
import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.entity.RoomType;
import org.junit.jupiter.api.Test;

class RoomTypeMapperTest {

    RoomTypeMapper roomTypeMapper = new RoomTypeMapper();

    RoomType roomTypeEntity = new RoomType(1L,"singleEntity", 200);

    RoomTypeDTO roomTypeDto = new RoomTypeDTO(2L,"singleDto", 200);





    @Test
    void testEntityToDto() {

        RoomTypeDTO roomTypeDTOFromEntity = roomTypeMapper.toDTO(roomTypeEntity);

        assertEquals(roomTypeEntity.getId(), roomTypeDTOFromEntity.getId());
        assertEquals(1L, roomTypeDTOFromEntity.getId());

        assertEquals("singleEntity", roomTypeDTOFromEntity.getName());
        assertEquals(roomTypeEntity.getName(), roomTypeDTOFromEntity.getName());


    }

    @Test
    void testDtoToEntity() {

        RoomType roomTypeEntityFromDto = roomTypeMapper.toEntity(roomTypeDto);

        assertEquals(roomTypeDto.getId(), roomTypeEntityFromDto.getId());
        assertEquals(2L, roomTypeEntityFromDto.getId());

        assertEquals("singleDto", roomTypeEntityFromDto.getName());
        assertEquals(roomTypeDto.getName(), roomTypeEntityFromDto.getName());



    }

}