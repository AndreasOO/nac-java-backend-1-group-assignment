package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
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
    @JoinColumn(name = "bookables_id")
    private List<Bookable> bookables;

    @OneToMany
    @JoinColumn(name = "extra_id")
    private List<Extra> extras;

    public Booking(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
