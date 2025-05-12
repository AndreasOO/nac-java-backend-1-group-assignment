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
public class Room extends Bookable implements Serializable {

    private Long roomCapacity;
    private Long extrasAvailable;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;


}