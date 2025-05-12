package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Extra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "bookable_id")
    private Bookable bookable;

    @ManyToOne
    @JoinColumn(name = "extra_type_id")
    private ExtraType extraType;

    public Extra() {

    }

    public Extra(Long id, Booking booking, Bookable bookable) {
        this.id = id;
        this.booking = booking;
        this.bookable = bookable;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Booking getBooking() { return booking; }

    public void setBooking(Booking booking) { this.booking = booking; }

    public Bookable getBookable() { return bookable; }

    public void setBookable(Bookable bookable) { this.bookable = bookable; }

    public ExtraType getExtraType() { return extraType; }

    public void setExtraType(ExtraType extraType) { this.extraType = extraType; }
}
