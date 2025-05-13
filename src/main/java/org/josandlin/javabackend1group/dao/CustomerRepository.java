package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository <Customer, Long> {
    Customer findByName(String name);

    @Query("SELECT b FROM Booking b WHERE b.customer = ?1")
    List<Booking> findBookingsByCustomer(Customer customer);

//    @Query("SELECT c FROM Customer c JOIN c.bookings b WHERE b.startDate >= :startDate AND b.endDate <= :endDate")
//    List<Customer> findCustomersBookingsAfter(@Param("startDate") LocalDate startDate,
//                                              @Param("endDate") LocalDate endDate);


    //

    /// @Modifying
    /// @Transactional
    /// @Query
}
