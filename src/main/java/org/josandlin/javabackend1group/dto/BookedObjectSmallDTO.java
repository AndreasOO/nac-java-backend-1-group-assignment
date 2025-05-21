package org.josandlin.javabackend1group.dto;

import java.time.LocalDate;
import java.util.List;

public class BookedObjectSmallDTO {

    Long id;
    RoomDTO room;
    List<AddedExtraDTO> extras;
    LocalDate startDate;
    LocalDate endDate;

    public BookedObjectSmallDTO(Long id, RoomDTO room, List<AddedExtraDTO> extras, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.room = room;
        this.extras = extras;
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
