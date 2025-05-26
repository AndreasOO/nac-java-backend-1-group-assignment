package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.BookingDao;
import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final BookingDao bookingDao;
    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;


    @Autowired
    public CustomerServiceImpl(BookingDao bookingDao,
                               CustomerDao customerDao, CustomerMapper customerMapper) {
        this.bookingDao = bookingDao;
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }

//    @Override
//    public Customer createAccount(Customer customer) {
//        if (findByName(customer.getName()) != null) {
//            throw new IllegalArgumentException("Customer already exists!");
//        }
//        customerDao.save(customer);
//        return customer;
//    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerDao.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));
        return customerMapper.toDTO(customer);
    }


//    @Override
//    public CustomerDTO findByName(String name) {
//        Customer customer = customerDao.findByName(name);
//        if (customer == null) {
//            throw new IllegalArgumentException("Customer not found with name: " + name);
//        }
//        return customerMapper.toDTO(customer);
//    }

    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerDao.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO editCustomer(CustomerDTO customerDTO) {
        Customer customer = customerDao.findById(customerDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerDTO.getId()));
        customer.setName(customerDTO.getName());
        Customer updated = customerDao.save(customer);
        return customerMapper.toDTO(updated);
    }


//    @Override
//    public void deleteCustomer(Long customerId) {
//        boolean hasBookings = bookingDao.findAll().stream().anyMatch(booking -> booking.getCustomer().getId().equals(customer.getId()));
//        if (hasBookings) {
//            //TODO add Optional.fail?
//            throw new IllegalArgumentException("Customer has active bookings, cannot delete");
//        } else {
//            customerDao.delete(customerId);
//        }
//
//    }

//    @Override
//    public void deleteCustomer(Long customerId) {
//        Customer customer = customerDao.findById(customerId)
//                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
//        boolean hasBookings = bookingDao.findAll().stream()
//                .anyMatch(booking -> booking.getCustomer().getId().equals(customer.getId()));
//        if (hasBookings) {
//            throw new IllegalArgumentException("Customer has active bookings, please try again!");
//        }
//        customerDao.delete(customer);
//    }

    @Override
    public boolean deleteCustomer(CustomerDTO customerDTO) {
        Customer customer = customerDao.findById(customerDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerDTO.getId()));
        boolean hasBookings = bookingDao.findAll().stream()
                .anyMatch(booking -> booking.getCustomer().getId().equals(customer.getId()));
        if (hasBookings) {
            return false;
        }
        customerDao.delete(customer);
        return true;
    }
}


//    @Override
//    public Customer findById(Long id){
//        return customerDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
//    }

    //  @Override
    //    public Customer editCustomer(Customer customer) {
    //
    //        return customerDao.save(customer);
    //    }


