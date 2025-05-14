package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int cost;

    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

    public RoomType(String name, int cost, List<Room> rooms) {
        this.name = name;
        this.cost = cost;
        this.rooms = rooms;
    }
}
