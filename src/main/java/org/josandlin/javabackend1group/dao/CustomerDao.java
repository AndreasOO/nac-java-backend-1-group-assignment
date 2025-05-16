package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerDao extends JpaRepository <Customer, Long> {


}
