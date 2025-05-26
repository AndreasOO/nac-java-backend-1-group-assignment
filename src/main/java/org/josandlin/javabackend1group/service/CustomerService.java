package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CustomerService {

//    @Transactional
//    Customer createAccount(Customer customer);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO findCustomerById(Long id);

    /// CustomerDTO findByName(String name);




    @Transactional
    CustomerDTO registerCustomer(CustomerDTO customerDTO);

    @Transactional
    CustomerDTO editCustomer(CustomerDTO customerDTO);

    @Transactional
    boolean deleteCustomer(CustomerDTO customerDTO);



}

// Customer findById(Long id);

//    @Transactional
//    Customer registerCustomer(Customer customer);

//    @Transactional
//    Customer editCustomer(Customer customer);