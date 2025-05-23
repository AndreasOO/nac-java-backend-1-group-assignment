package org.josandlin.javabackend1group.util;

import java.time.LocalDate;

public class BookedObjectUtil {

    Long bookingId;
    Long bookedObjectId;
    Long roomId;
    LocalDate startDate;
    LocalDate endDate;

    public BookedObjectUtil(Long bookingId, Long bookedObjectId, Long roomId, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookedObjectUtil(Long bookingId, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookedObjectUtil(){

    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookedObjectId() {
        return bookedObjectId;
    }

    public void setBookedObjectId(Long bookedObjectId) {
        this.bookedObjectId = bookedObjectId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
