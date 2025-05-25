package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class RoomMapperTest {
    RoomMapper roomMapper = new RoomMapper(new RoomTypeMapper());

    Room roomEntity = new Room(1L,
                    "roomFromEntity",
                    3,
                    1,
                    new RoomType(2L,"singleEntity", 200));


    RoomDTO roomDto = new RoomDTO(3L,
            "roomFromDto",
            3,
            1,
            new RoomTypeDTO(4L,"singleDto", 200));




    @Test
    void testEntityToDto() {

        RoomDTO roomDTOFromEntity = roomMapper.toDTO(roomEntity);

        assertEquals(roomEntity.getId(), roomDTOFromEntity.getId());
        assertEquals(1L, roomDTOFromEntity.getId());

        assertEquals("roomFromEntity", roomDTOFromEntity.getName());
        assertEquals(roomEntity.getName(), roomDTOFromEntity.getName());

        assertEquals(2L, roomDTOFromEntity.getRoomType().getId());
        assertEquals(roomEntity.getRoomType().getId(), roomDTOFromEntity.getRoomType().getId());

        assertEquals("singleEntity", roomDTOFromEntity.getRoomType().getName());
        assertEquals(roomEntity.getRoomType().getName(), roomDTOFromEntity.getRoomType().getName());


    }

    @Test
    void testDtoToEntity() {

        Room roomEntityFromDto = roomMapper.toEntity(roomDto);

        assertEquals(roomDto.getId(), roomEntityFromDto.getId());
        assertEquals(3L, roomEntityFromDto.getId());

        assertEquals("roomFromDto", roomEntityFromDto.getName());
        assertEquals(roomDto.getName(), roomEntityFromDto.getName());

        assertEquals(4L, roomEntityFromDto.getRoomType().getId());
        assertEquals(roomDto.getRoomType().getId(), roomEntityFromDto.getRoomType().getId());

        assertEquals("singleDto", roomEntityFromDto.getRoomType().getName());
        assertEquals(roomDto.getRoomType().getName(), roomEntityFromDto.getRoomType().getName());


    }

}