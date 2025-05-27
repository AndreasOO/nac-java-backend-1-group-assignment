package org.josandlin.javabackend1group.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.josandlin.javabackend1group.entity.RoomType;

public class RoomDTO extends AbstractDTO {

    private Long id;

    private String name;
    private int maxCapacity;
    private int extraBedsAvailable;
    private RoomTypeDTO roomType;

    public RoomDTO(Long id, String name, int maxCapacity, int extraBedsAvailable, RoomTypeDTO roomType) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.extraBedsAvailable = extraBedsAvailable;
        this.roomType = roomType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getExtraBedsAvailable() {
        return extraBedsAvailable;
    }

    public void setExtraBedsAvailable(int extraBedsAvailable) {
        this.extraBedsAvailable = extraBedsAvailable;
    }

    public RoomTypeDTO getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeDTO roomType) {
        this.roomType = roomType;
    }
}
