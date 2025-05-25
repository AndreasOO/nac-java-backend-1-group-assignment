package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {


    CustomerMapper customerMapper = new CustomerMapper();

    Customer customerEntity = new Customer(1L, "customerFromEntity");
    CustomerDTO customerDTO = new CustomerDTO(2L, "customerFromDto");




    @Test
    void testEntityToDto() {

        CustomerDTO customerDTOFromEntity = customerMapper.toDTO(customerEntity);


        assertEquals(1L, customerDTOFromEntity.getId());
        assertEquals(customerEntity.getId(), customerDTOFromEntity.getId());


        assertEquals("customerFromEntity", customerDTOFromEntity.getName());
        assertEquals(customerEntity.getName(), customerDTOFromEntity.getName());


    }

    @Test
    void testDtoToEntity() {

        Customer customerEntityFromDto = customerMapper.toEntity(customerDTO);


        assertEquals(2L, customerEntityFromDto.getId());
        assertEquals(customerDTO.getId(), customerEntityFromDto.getId());


        assertEquals("customerFromDto", customerEntityFromDto.getName());
        assertEquals(customerDTO.getName(), customerEntityFromDto.getName());


    }
}