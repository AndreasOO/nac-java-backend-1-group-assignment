package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer entity) {
        if (entity == null) {
            return null;
        }
        return new CustomerDTO(entity.getId(), entity.getName());
    }

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }
        Customer customer = new Customer();
        if (dto.getId() != null) {
            customer.setId(dto.getId());
        }
        customer.setName(dto.getName());
        return customer;
    }
}
