package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;



import java.io.Serializable;
import java.util.Objects;


@Entity
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int maxCapacity;
    private int extraBedsAvailable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private RoomType roomType;

    public Room(Long id, String name, int maxCapacity, int extraBedsAvailable, RoomType roomType) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.extraBedsAvailable = extraBedsAvailable;
        this.roomType = roomType;
    }

    public Room(String name, int maxCapacity, int extraBedsAvailable, RoomType roomType) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.extraBedsAvailable = extraBedsAvailable;
        this.roomType = roomType;
    }

    public Room(){

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

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
