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

    // flytta till bookedroom
    private LocalDate startDate;
    private LocalDate endDate;

    public Booking(Customer customer, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
