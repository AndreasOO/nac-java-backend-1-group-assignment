package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
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

    @OneToMany(mappedBy = "booking")
    private List<BookedRoom> bookedRoom;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    private Date startDate;
    private Date endDate;

    public Booking(List<BookedRoom> bookedRoom, Customer customer, Date startDate, Date endDate) {
        this.bookedRoom = bookedRoom;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
