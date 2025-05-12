package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
abstract class Bookable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking bookings;


    @OneToMany
    @JoinColumn(name = "extra_id")
    private List<Extra> extras;

    public Bookable() {

    }
    public Bookable(Long id) { this.id = id; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Booking getBookings() {
        return bookings;
    }

    public void setBookings(Booking bookings) {
        this.bookings = bookings;
    }

    public List<Extra> getExtras() { return extras; }

    public void setExtras(List<Extra> extras) { this.extras = extras; }
}
