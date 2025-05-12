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
public class Extra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "bookable_id")
    private Bookable bookable;

    @ManyToOne
    @JoinColumn(name = "extra_type_id")
    private ExtraType extraType;


    public Extra(Long id, Booking booking, Bookable bookable) {
        this.id = id;
        this.booking = booking;
        this.bookable = bookable;
    }

}
