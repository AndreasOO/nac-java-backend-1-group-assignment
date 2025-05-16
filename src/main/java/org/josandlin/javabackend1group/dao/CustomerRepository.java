package org.josandlin.javabackend1group.dao;

import jakarta.transaction.Transactional;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface CustomerRepository extends JpaRepository <Customer, Long> {
    Customer findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Customer c " +
            "WHERE c.id = :id " +
            "AND NOT EXISTS (" +
            "   SELECT b FROM Booking b WHERE b.customer.id = c.id" +
            ")")
    void deleteCustomerIfNoBookings(@Param("id") Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.name = :newName WHERE c.id = :id")
    void updateCustomer(@Param("newName") String newName, @Param("id") Long id);


}
