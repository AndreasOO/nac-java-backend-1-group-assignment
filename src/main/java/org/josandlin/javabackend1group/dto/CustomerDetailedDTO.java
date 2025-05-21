package org.josandlin.javabackend1group.dto;

import java.util.List;

public class CustomerDetailedDTO {

    Long id;
    String name;
    List<BookingDetailedDTO> booking;

    public CustomerDetailedDTO(Long id, String name, List<BookingDetailedDTO> booking) {
        this.id = id;
        this.name = name;
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookingDetailedDTO> getBooking() {
        return booking;
    }

    public void setBooking(List<BookingDetailedDTO> booking) {
        this.booking = booking;
    }
}
