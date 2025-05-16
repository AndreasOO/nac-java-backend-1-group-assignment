package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class BookedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Room room;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="booked_room_id")
    private List<AddedExtra> extras;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    private LocalDate startDate;
    private LocalDate endDate;

    public BookedObject(Long id, Room room, List<AddedExtra> extras, Booking booking, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.room = room;
        this.extras = extras;
        this.booking = booking;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookedObject(Room room, List<AddedExtra> extras, Booking booking, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.extras = extras;
        this.booking = booking;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookedObject(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<AddedExtra> getExtras() {
        return extras;
    }

    public void setExtras(List<AddedExtra> extras) {
        this.extras = extras;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
