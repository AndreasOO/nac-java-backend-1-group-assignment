package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@Entity
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

    public Bookable(Long id) { this.id = id; }

}
