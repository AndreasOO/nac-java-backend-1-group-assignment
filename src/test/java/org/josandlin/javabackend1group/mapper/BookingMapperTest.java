package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.*;
import org.josandlin.javabackend1group.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class BookingMapperTest {

    BookingMapper bookingMapper = new BookingMapper(new CustomerMapper());

    Booking bookingEntity =
            new Booking(1L, new Customer(2L, "customerFromEntity"));

    BookingDTO bookingDTO =
            new BookingDTO(3L, new CustomerDTO(4L, "customerFromDTO"));


    @Test
    void testEntityToDto() {
        BookingDTO bookingDTOFromEntity = bookingMapper.toDTO(bookingEntity);


        assertEquals(1L, bookingDTOFromEntity.getId());
        assertEquals(bookingEntity.getId(), bookingDTOFromEntity.getId());

        assertEquals(2L, bookingDTOFromEntity.getCustomer().getId());
        assertEquals(bookingEntity.getCustomer().getId(), bookingDTOFromEntity.getCustomer().getId());

        assertEquals("customerFromEntity", bookingDTOFromEntity.getCustomer().getName());
        assertEquals(bookingEntity.getCustomer().getName(), bookingDTOFromEntity.getCustomer().getName());


    }

    @Test
    void testDtoToEntity() {
        Booking bookingEntityFromDto = bookingMapper.toEntity(bookingDTO);


        assertEquals(3L, bookingEntityFromDto.getId());
        assertEquals(bookingDTO.getId(), bookingEntityFromDto.getId());

        assertEquals(4L, bookingEntityFromDto.getCustomer().getId());
        assertEquals(bookingDTO.getCustomer().getId(), bookingEntityFromDto.getCustomer().getId());

        assertEquals("customerFromDTO", bookingEntityFromDto.getCustomer().getName());
        assertEquals(bookingDTO.getCustomer().getName(), bookingEntityFromDto.getCustomer().getName());


    }
}