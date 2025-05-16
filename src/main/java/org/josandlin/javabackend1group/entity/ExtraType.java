package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;



@Entity
public class ExtraType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int cost;

    public ExtraType(Long id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public ExtraType(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public ExtraType(){

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
