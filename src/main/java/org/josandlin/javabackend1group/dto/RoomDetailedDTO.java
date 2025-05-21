package org.josandlin.javabackend1group.dto;

public class RoomDetailedDTO {

    Long roomId;
    String roomName;
    int maxCapacity;
    int extraBedsAvailable;

    Long roomTypeId;
    String roomTypeName;
    int costPerNight;

    public RoomDetailedDTO(Long roomId, String roomName, int maxCapacity, int extraBedsAvailable, Long roomTypeId, String roomTypeName, int costPerNight) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
        this.extraBedsAvailable = extraBedsAvailable;
        this.roomTypeId = roomTypeId;
        this.roomTypeName = roomTypeName;
        this.costPerNight = costPerNight;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public int getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(int costPerNight) {
        this.costPerNight = costPerNight;
    }
}
