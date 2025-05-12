package org.josandlin.javabackend1group.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "bookings_id")
    private List<Booking> bookings;


    public Customer() {

    }
    public Customer(Long id, String name, List<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.bookings = bookings;
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Booking> getBookings() { return bookings; }

    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}
