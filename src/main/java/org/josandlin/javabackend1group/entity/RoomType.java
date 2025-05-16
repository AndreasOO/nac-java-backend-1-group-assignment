package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int costPerNight;

    public RoomType(Long id, String name, int costPerNight) {
        this.id = id;
        this.name = name;
        this.costPerNight = costPerNight;
    }

    public RoomType(String name, int costPerNight) {
        this.name = name;
        this.costPerNight = costPerNight;
    }

    public RoomType(){

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

    public int getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(int costPerNight) {
        this.costPerNight = costPerNight;
    }
}
