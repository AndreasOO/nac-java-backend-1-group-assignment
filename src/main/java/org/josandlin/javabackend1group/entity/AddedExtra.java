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
public class AddedExtra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "extra_type_id")
    private ExtraType extraType;

    @ManyToOne
    @JoinColumn(name="booked_room_id")
    private BookedRoom bookedRoom;

    private int quantity;

    public AddedExtra(ExtraType extraType, BookedRoom bookedRoom, int quantity) {
        this.extraType = extraType;
        this.bookedRoom = bookedRoom;
        this.quantity = quantity;
    }
}
