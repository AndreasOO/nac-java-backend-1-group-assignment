package org.josandlin.javabackend1group.dao;

import jakarta.transaction.Transactional;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CustomerDao extends JpaRepository <Customer, Long> {
    Customer findByName(String name);

    Customer registerCustomer(Customer customer);

    Customer editCustomer(Customer customer);

    Customer deleteCustomer(Long id);

    Customer saveCustomer(Customer customer);

}
