package org.josandlin.javabackend1group.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.josandlin.javabackend1group.entity.Customer;


public class BookingDTO {

    private Long id;

    @Valid
    @NotNull(message="Customer can't be null")
    private CustomerDTO customerDTO;

    public BookingDTO(Long id, CustomerDTO customerDTO) {
        this.id = id;
        this.customerDTO = customerDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customerDTO;
    }

    public void setCustomer(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
