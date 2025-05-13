package org.josandlin.javabackend1group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("customers")
public class CustomerController {

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




}
