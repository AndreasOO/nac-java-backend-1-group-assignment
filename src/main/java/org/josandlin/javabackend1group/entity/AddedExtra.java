package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class AddedExtra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private ExtraType extraType;

    public AddedExtra(Long id, ExtraType extraType) {
        this.id = id;
        this.extraType = extraType;
    }

    public AddedExtra(ExtraType extraType) {
        this.extraType = extraType;
    }

    public AddedExtra(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtraType getExtraType() {
        return extraType;
    }

    public void setExtraType(ExtraType extraType) {
        this.extraType = extraType;
    }
}
