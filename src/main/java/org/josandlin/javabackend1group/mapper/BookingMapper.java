package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    private final CustomerMapper customerMapper;

    @Autowired
    public BookingMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public BookingDTO toDTO(Booking entity) {
        if (entity == null) {
            return null;
        }
        return new BookingDTO(entity.getId(), customerMapper.toDTO(entity.getCustomer()));
    }

    public Booking toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Booking(dto.getId(), customerMapper.toEntity(dto.getCustomer()));
    }
}
