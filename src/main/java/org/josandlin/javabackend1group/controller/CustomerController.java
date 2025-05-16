package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.service.CustomerService;
import org.josandlin.javabackend1group.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/register")
    public String showCustomerRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "registration";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
        try {
            customerService.createAccount(customer);
            return "redirect:/customers/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }

//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("customer", new Customer());
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@ModelAttribute("name") String name, Model model) {
//        try{
//            Customer customer = customerService.logIn(name);
//            return "redirect:/customers/customer/{customerId???}";
//        }catch(IllegalArgumentException e){
//            model.addAttribute("error", e.getMessage());
//            return "login";
//        }
//    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public String editCustomer(Model model, @PathVariable long id, @RequestParam String newName) {
        customerDao.updateCustomer(newName, id);
        customerDao.flush();
        model.addAttribute("customer", customerDao.findById(id).get());
        System.out.println("New name on customer registered " + id + " " + newName);
        //TODO use service here
        return "edit-customer"; //Tl-sida funkar inte men tas sen
    }

    @DeleteMapping("/remove/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerDao.findById(id);
        if (optionalCustomer.isPresent()) {
            try {
                customerDao.deleteCustomerIfNoBookings(id);
                model.addAttribute("message", "Customer removed if no bookings were present.");
            } catch (Exception e) {
                model.addAttribute("message", "Could not remove customer: " + e.getMessage());
            }
        } else {
            model.addAttribute("message", "Customer not found.");
        }
        model.addAttribute("customer", optionalCustomer);
        System.out.println("deleting customer " + id + " " + optionalCustomer);
        //TODO use service here
        return "delete"; //Tl-sida funkar ej men det tar vi sen
    }

    @GetMapping("customer/name/{name}")
    @ResponseBody
    public String findCustomerByName(Model model, @PathVariable String name) {
        Customer customer = customerDao.findByName(name);
        model.addAttribute("customer", customer);
        return "customer-details";
    }

    @GetMapping("/customer/id/{id}")
    @ResponseBody
    public Optional<Customer> findCustomerById(@PathVariable Long id) {
        return customerDao.findById(id);
    }
}
