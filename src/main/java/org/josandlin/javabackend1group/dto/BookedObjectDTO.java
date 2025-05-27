package org.josandlin.javabackend1group.dto;

import jakarta.persistence.*;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Room;

import java.time.LocalDate;
import java.util.List;

public class BookedObjectDTO extends AbstractDTO {

    private Long id;
    private RoomDTO room;
    private List<AddedExtraDTO> extras;
    private BookingDTO booking;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookedObjectDTO(Long id, RoomDTO room, List<AddedExtraDTO> extras, BookingDTO booking, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.room = room;
        this.extras = extras;
        this.booking = booking;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public List<AddedExtraDTO> getExtras() {
        return extras;
    }

    public void setExtras(List<AddedExtraDTO> extras) {
        this.extras = extras;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
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
