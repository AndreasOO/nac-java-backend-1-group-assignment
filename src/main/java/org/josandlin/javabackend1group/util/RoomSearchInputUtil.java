package org.josandlin.javabackend1group.util;

import java.time.LocalDate;

public class RoomSearchInputUtil {

    boolean update;
    Long bookingId;
    Long bookedObjectId = 0L;
    Integer guestCount = 1;
    LocalDate startDate;
    LocalDate endDate;

    public RoomSearchInputUtil(boolean update, Long bookingId, Long bookedObjectId, Integer guestCount, LocalDate startDate, LocalDate endDate) {
        this.update = update;
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
        this.guestCount = guestCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RoomSearchInputUtil(boolean update, Long bookingId, Long bookedObjectId) {
        this.update = update;
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
    }

    public RoomSearchInputUtil(boolean update, Long bookingId) {
        this.update = update;
        this.bookingId = bookingId;
    }

    public RoomSearchInputUtil(){

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

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
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
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
