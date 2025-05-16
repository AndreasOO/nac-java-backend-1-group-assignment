package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer createAccount(Customer customer) {
        if (customerDao.findByName(customer.getName()) != null) {
            throw new IllegalArgumentException("Customer already exists!");
        }
        customerDao.save(customer);
        return customer;
    }

//    @Override
//    public Customer logIn(String name){
//        Customer loggedInCustomer = customerDao.findByName(name);
//        if (loggedInCustomer == null) {
//            throw new IllegalArgumentException("Customer doesn't exist!");
//        }
//        return loggedInCustomer;
//    }
}
