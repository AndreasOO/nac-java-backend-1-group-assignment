package org.josandlin.javabackend1group.controller;

import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.josandlin.javabackend1group.dao.CustomerRepository;
import org.josandlin.javabackend1group.dao.CustomerRepository;
import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {

    CustomerRepository customerDao;

    public CustomerController(CustomerRepository customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping("register")
    public String showCustomerRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("register")
    public String registerCustomer(Model model) {
        System.out.println("registering customer");
        //TODO use service here
        return "registration";
    }

    @PutMapping("register/{id}")
    public String editCustomer(Model model, @PathVariable String id) {
        System.out.println("registering customer " + id);
        //TODO use service here
        return "registration";
    }

    @DeleteMapping("register/{id}")
    public String deleteCustomer(Model model, @PathVariable String id) {
        System.out.println("deleting customer " + id);
        //TODO use service here
        return "registration";
    }

    @GetMapping("bookings/{id}")
    public List<Booking> findBookingsByCustomer(String name){
        Customer customer = customerDao.findByName(name);
        return customerDao.findBookingsByCustomer(customer);
    }

    @GetMapping("customer/{name}")
    public Customer findCustomerByName(Model model, @PathVariable String name) {
        model.addAttribute("customer", customerDao.findByName(name));
        return customerDao.findByName(name);
    }

//    @GetMapping("customere/{name}")
//    @ResponseBody
//    public Customer findCustomerByNamee(@PathVariable String name) {
//        return customerDao.findByName(name);
//    }


//    @GetMapping("customer/{id}")
//    public Customer findCustomerById(@PathVariable Long id) {
//        return customer.find
//    }

}
