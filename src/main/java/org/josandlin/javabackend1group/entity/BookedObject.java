package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Room room;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="booked_room_id")
    private List<AddedExtra> extras;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    private LocalDate startDate;
    private LocalDate endDate;

    public BookedObject(Room room, List<AddedExtra> extras, Booking booking, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.extras = extras;
        this.booking = booking;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
