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

    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany
    @JoinColumn(name = "bookable_id")
    private List<Bookable> bookables;

    @OneToMany
    @JoinColumn(name = "extra_id")
    private List<Extra> extras;

}
