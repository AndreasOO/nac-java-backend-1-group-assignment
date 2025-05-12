package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;

@Setter
@Getter
@Data
@NoArgsConstructor
@Entity
public class Room extends Bookable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long roomCapacity;
    private Long extrasAvailable;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;


    public Room(Long id, Long roomCapacity, Long extrasAvailable) {
        this.id = id;
        this.roomCapacity = roomCapacity;
        this.extrasAvailable = extrasAvailable;
    }

}
