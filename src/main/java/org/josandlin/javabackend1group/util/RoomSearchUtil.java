package org.josandlin.javabackend1group.util;

import java.time.LocalDate;

public class RoomSearchUtil {

    boolean update;
    Long bookingId;
    Long bookedObjectId = 0L;
    Integer questCount = 1;
    LocalDate startDate;
    LocalDate endDate;

    public RoomSearchUtil(boolean update, Long bookingId, Long bookedObjectId, Integer questCount, LocalDate startDate, LocalDate endDate) {
        this.update = update;
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
        this.questCount = questCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RoomSearchUtil(boolean update, Long bookingId, Long bookedObjectId) {
        this.update = update;
        this.bookingId = bookingId;
        this.bookedObjectId = bookedObjectId;
    }

    public RoomSearchUtil(boolean update, Long bookingId) {
        this.update = update;
        this.bookingId = bookingId;
    }

    public RoomSearchUtil(){

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

    public Integer getQuestCount() {
        return questCount;
    }

    public void setQuestCount(Integer questCount) {
        this.questCount = questCount;
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
