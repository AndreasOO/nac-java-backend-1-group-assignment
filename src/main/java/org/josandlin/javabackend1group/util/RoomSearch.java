package org.josandlin.javabackend1group.util;

import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public class RoomSearch {

    Long bookedObjectId = -1L;
    Long bookingId;
    int guestCount;
    LocalDate startDate;
    LocalDate endDate;
    boolean isUpdate;

    public RoomSearch(Long bookedObjectId, Long bookingId, int guestCount, LocalDate startDate, LocalDate endDate) {
        this.bookedObjectId = bookedObjectId;
        this.bookingId = bookingId;
        this.guestCount = guestCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RoomSearch() {

    }

    public Long getBookedObjectId() {
        return bookedObjectId;
    }

    public void setBookedObjectId(Long bookedObjectId) {
        this.bookedObjectId = bookedObjectId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
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

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
