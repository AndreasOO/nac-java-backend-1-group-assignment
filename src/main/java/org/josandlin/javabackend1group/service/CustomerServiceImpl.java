package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.BookingDao;
import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final BookingDao bookingDao;
    private final CustomerDao customerDao;



    @Autowired
    public CustomerServiceImpl(BookingDao bookingDao,
                               CustomerDao customerDao) {
        this.bookingDao = bookingDao;
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

    @Override
    public List<Customer> getAllCustomers(){
        return customerDao.findAll();
    }

//    @Override
//    public Customer logIn(String name){
//        Customer loggedInCustomer = customerDao.findByName(name);
//        if (loggedInCustomer == null) {
//            throw new IllegalArgumentException("Customer doesn't exist!");
//        }
//        return loggedInCustomer;
//    }



    @Override
    public Customer registerCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer editCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        boolean hasBookings = bookingDao.findAll().stream().anyMatch(booking -> booking.getCustomer().getId().equals(customer.getId()));
        if (hasBookings) {
            //TODO add Optional.fail?
            throw new IllegalArgumentException("Customer has active bookings, cannot delete");
        } else {
            customerDao.delete(customer);
        }

    }
}
