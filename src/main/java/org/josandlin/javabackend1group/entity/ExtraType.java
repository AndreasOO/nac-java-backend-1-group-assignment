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

    @OneToMany
    @JoinColumn(name = "extras_id")
    private List<Extra> extras;

    public ExtraType() {

    }

    public ExtraType(Long id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getCost() { return cost; }

    public void setCost(int cost) { this.cost = cost; }

    public List<Extra> getExtras() { return extras; }

    public void setExtras(List<Extra> extras) { this.extras = extras; }
}
