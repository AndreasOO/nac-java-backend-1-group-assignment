package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int roomCapacity;
    private int extrasAvailable;

    @ManyToOne
    @JoinColumn
    private RoomType roomType;

    public Room(String name, int roomCapacity, int extrasAvailable, RoomType roomType) {
        this.name = name;
        this.roomCapacity = roomCapacity;
        this.extrasAvailable = extrasAvailable;
        this.roomType = roomType;
    }
}
