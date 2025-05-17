package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface CustomerService {

    @Transactional
    Customer createAccount(Customer customer);

    List<Customer> getAllCustomers();

//    Customer logIn(String name);


}
