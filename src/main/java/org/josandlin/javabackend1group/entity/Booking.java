package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    public Booking(Customer customer) {
        this.customer = customer;
    }
}
