package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookedRoom {
    // döp om till bookedObject
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Room room;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="booked_room_id")
    List<AddedExtra> extras;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    public BookedRoom(Room room, Booking booking) {
        this.room = room;
        this.booking = booking;
    }
}
