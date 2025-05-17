package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CustomerService {

    @Transactional
    Customer createAccount(Customer customer);

    List<Customer> getAllCustomers();

//    Customer logIn(String name);

    Customer findById(Long id);


    @Transactional
    Customer registerCustomer(Customer customer);

    @Transactional
    Customer editCustomer(Customer customer);

    @Transactional
    void deleteCustomer(Customer customer);



}
