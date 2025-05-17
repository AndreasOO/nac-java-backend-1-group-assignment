package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface CustomerService {

    @Transactional
    Customer registerCustomer(Customer customer);

    @Transactional
    Customer editCustomer(Customer customer);

    @Transactional
    void deleteCustomer(Customer customer);



}
