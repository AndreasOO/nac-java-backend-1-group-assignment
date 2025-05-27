package org.josandlin.javabackend1group.service;

import jakarta.validation.Valid;
import org.josandlin.javabackend1group.dao.BookingDao;
import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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

    @Transactional
    @Override
    public CustomerDTO registerCustomer(@Validated CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        System.out.println("Saving customer: " + customer);
        Customer savedCustomer = customerDao.save(customer);
        System.out.println("Saved Customer: " + savedCustomer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Transactional
    @Override
    public CustomerDTO editCustomer(@Validated CustomerDTO customerDTO) {
        Customer customer = customerDao.findById(customerDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerDTO.getId()));
        customer.setName(customerDTO.getName());
        Customer updated = customerDao.save(customer);
        return customerMapper.toDTO(updated);
    }

    @Transactional
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

