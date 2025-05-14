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
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerDao;

    @GetMapping("register")
    public String showCustomerRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("register")
    public String registerCustomer(Model model) {
        Customer customer = new Customer();
        customerDao.save(customer);
        System.out.println("registering customer");
        //TODO use service here
        return "registration";
    }

    @PutMapping("register/{id}")
    public String editCustomer(Model model, @PathVariable Long id, @RequestParam String newName) {
        customerDao.updateCustomer(newName, id);
        model.addAttribute("customer", customerDao.findByName(newName));
        System.out.println("New name on customer registered " + id + " " + newName);
        //TODO use service here
        return "registration";
    }

    @DeleteMapping("register/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
            Optional<Customer> optionalCustomer = customerDao.findById(id);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                if (customer.getBookings() == null || customer.getBookings().isEmpty()) {
                    customerDao.deleteIfNoBookings(id);
                    model.addAttribute("message", "Customer removed successfully.");
                } else {
                    model.addAttribute("message", "Customer has reservations, cannot be removed.");
                }
            } else {
                model.addAttribute("message", "Customer not found.");
            }
        model.addAttribute("customer", optionalCustomer);
        System.out.println("deleting customer " + id + " " + optionalCustomer);
        //TODO use service here
        return "registration";
    }

//    @GetMapping("bookings/{id}")
//    public List<Booking> findBookingsByCustomer(String name){
//        Customer customer = customerDao.findByName(name);
//        return customerDao.findBookingsByCustomer(customer);
//    }


    @GetMapping("customer/name/{name}")
    public String findCustomerByName(Model model, @PathVariable String name) {
        Customer customer = customerDao.findByName(name);
        model.addAttribute("customer", customer);
        return "customer-details";
    }

    @GetMapping("/customer/id/{id}")
    public Optional<Customer> findCustomerById(@PathVariable Long id) {
        return customerDao.findById(id);
    }

}
