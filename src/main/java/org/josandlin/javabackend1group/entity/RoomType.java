package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class RoomType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "rooms_id")
    private List<Room> rooms;

    public RoomType() {

    }
    public RoomType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Room> getRooms() { return rooms; }

    public void setRooms(List<Room> rooms) { this.rooms = rooms; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


}
