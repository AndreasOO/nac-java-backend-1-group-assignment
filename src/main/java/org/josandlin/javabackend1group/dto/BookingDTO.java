package org.josandlin.javabackend1group.dto;

import org.josandlin.javabackend1group.entity.Customer;


public class BookingDTO extends AbstractDTO {

    private Long id;
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
