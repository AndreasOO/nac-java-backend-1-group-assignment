package org.josandlin.javabackend1group.util;

import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public class RoomSearch {

    Long bookingId;
    Long bookedObjectId = 0L;
    Integer guestCount = 1;
    LocalDate startDate;
    LocalDate endDate;
    boolean isUpdate;

    public RoomSearch(Long bookingId, Long bookedObjectId, int guestCount, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
        this.guestCount = guestCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RoomSearch(boolean isUpdate, Long bookingId) {
        this.isUpdate = isUpdate;
        this.bookingId = bookingId;
    }

    public RoomSearch(boolean isUpdate, Long bookingId, Long bookedObjectId) {
        this.isUpdate = isUpdate;
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
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
