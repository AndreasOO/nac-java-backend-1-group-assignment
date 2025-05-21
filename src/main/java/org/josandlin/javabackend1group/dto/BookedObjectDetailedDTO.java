package org.josandlin.javabackend1group.dto;

import jakarta.persistence.*;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Room;

import java.time.LocalDate;
import java.util.List;

public class BookedObjectDetailedDTO {

    private Long bookedObjectId;
    private RoomDetailedDTO room;
    private List<AddedExtraDetailedDTO> extras;
    private Long bookingId;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookedObjectDetailedDTO(Long bookedObjectId, RoomDetailedDTO room, List<AddedExtraDetailedDTO> extras, Long bookingId, LocalDate startDate, LocalDate endDate) {
        this.bookedObjectId = bookedObjectId;
        this.room = room;
        this.extras = extras;
        this.bookingId = bookingId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getBookedObjectId() {
        return bookedObjectId;
    }

    public void setBookedObjectId(Long bookedObjectId) {
        this.bookedObjectId = bookedObjectId;
    }

    public RoomDetailedDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDetailedDTO room) {
        this.room = room;
    }

    public List<AddedExtraDetailedDTO> getExtras() {
        return extras;
    }

    public void setExtras(List<AddedExtraDetailedDTO> extras) {
        this.extras = extras;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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
