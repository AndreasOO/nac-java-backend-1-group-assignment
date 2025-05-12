package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private List<Bookable> bookable;

    @OneToMany
    @JoinColumn(name = "extra_id")
    private List<Extra> extras;

    public Booking() {

    }

    public Booking(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<Bookable> getBookable() { return bookable; }

    public void setBookable(List<Bookable> bookable) { this.bookable = bookable; }

    public List<Extra> getExtras() { return extras; }

    public void setExtras(List<Extra> extras) { this.extras = extras; }
}
