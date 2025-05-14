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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    @OneToMany(mappedBy = "bookedRoom")
    private List<AddedExtra> addedExtras;

    public BookedRoom(Room room, Booking booking, List<AddedExtra> addedExtras) {
        this.room = room;
        this.booking = booking;
        this.addedExtras = addedExtras;
    }
}
