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
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Bookable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking bookings;

    @OneToMany
    @JoinColumn(name = "extra_id")
    private List<Extra> extras;

}
