package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.List;

@Entity
public class Room extends Bookable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long roomNumber;

    private Long roomCapacity;
    private Long extrasAvailable;

    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;

    public Room() {

    }

    public Room(Long roomNumber, Long roomCapacity, Long extrasAvailable) {
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
        this.extrasAvailable = extrasAvailable;
    }

    public Long getRoomNumber() { return roomNumber; }

    public void setRoomNumber(Long roomNumber) { this.roomNumber = roomNumber; }

    public Long getRoomCapacity() { return roomCapacity; }

    public void setRoomCapacity(Long roomCapacity) { this.roomCapacity = roomCapacity; }

    public Long getExtrasAvailable() { return extrasAvailable; }

    public void setExtrasAvailable(Long extrasAvailable) { this.extrasAvailable = extrasAvailable; }

    public RoomType getRoomType() { return roomType; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
}
