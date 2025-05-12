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
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "bookings_id")
    private List<Booking> bookings;

    public Customer(Long id, String name, List<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.bookings = bookings;
    }


}
