package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class BookedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    @Valid
    @NotNull(message="Booked object must involve a room")
    private Room room;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="booked_room_id")
    @Valid
    private List<AddedExtra> extras;

    @ManyToOne
    @JoinColumn
    @Valid
    @NotNull(message="Booked object must involve a booking")
    private Booking booking;

    @NotNull(message="Booked object must have a start date")
    private LocalDate startDate;

    @NotNull(message="Booked object must have an end date")
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookedObject that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
