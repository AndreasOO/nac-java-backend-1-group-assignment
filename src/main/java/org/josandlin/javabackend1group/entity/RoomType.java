package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data
@AllArgsConstructor
@Entity
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int costPerNight;

    public RoomType(){

    }

    public RoomType(String name, int costPerNight) {
        this.name = name;
        this.costPerNight = costPerNight;
    }
}
