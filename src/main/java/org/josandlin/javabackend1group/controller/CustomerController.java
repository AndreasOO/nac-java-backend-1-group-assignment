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
   // private CustomerDao customerDao;

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

    @PostMapping("/edit/{id}")
    @ResponseBody
    public String editCustomer(Model model, @PathVariable long id, @RequestParam String newName, Customer customer) {
        model.addAttribute("customer", customerService.findById(id));
        System.out.println("New name on customer registered " + id + " " + newName);
        customerService.editCustomer(customer);
        customerService.saveCustomer(customer);
        return "edit-customer"; //Tl-sida funkar inte men tas sen
    }

    @DeleteMapping("/remove/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id, Customer customer) {
        model.addAttribute("customer", customer);
        System.out.println("deleting customer " + id + " " + customer);
        customerService.deleteCustomer(customer);
        return "delete";
    }

    @GetMapping("customer/name/{name}")
    @ResponseBody
    public String findCustomerByName(Model model, @PathVariable String name) {
        Customer customer = customerService.findByName(name);
        model.addAttribute("customer", customer);
        return "customer-details";
    }

    @GetMapping("/customer/id/{id}")
    @ResponseBody
    public Customer findCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }
}
