package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dao.CustomerDao;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.service.CustomerService;
import org.josandlin.javabackend1group.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
   // private CustomerDao customerDao;

    private final CustomerService customerService;
//    @Autowired
//    private CustomerDao customerDao;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/register")
    public String showCustomerRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "registration";
    }


    @PostMapping("/add")
    public String registerCustomer(@RequestParam String name) {
        CustomerDTO customerDTOadd = new CustomerDTO(name);
        customerService.registerCustomer(customerDTOadd);
        return "redirect:/customers/all";
    }

    @PostMapping("/edit")
    public String editCustomer(@RequestParam Long id, @RequestParam String name) {
        CustomerDTO existingCustomer = customerService.findCustomerById(id);
        existingCustomer.setName(name);
        customerService.editCustomer(existingCustomer);
        return "redirect:/customers/all";
    }

    @DeleteMapping("/delete")
    public String removeCustomer(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        CustomerDTO customerToDelete = customerService.findCustomerById(id);
        boolean deleted = customerService.deleteCustomer(customerToDelete);
        redirectAttributes.addFlashAttribute("targetId", id);
        if (!deleted) {
            redirectAttributes.addFlashAttribute("error", "Customer has bookings, could not be deleted.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Customer has been deleted");
        }
        return "redirect:/customers/all";
    }

//    @PostMapping("/edit/{id}")
//    @ResponseBody
//    public String editCustomer(Model model, @PathVariable long id, String newName, Customer customer) {
//        model.addAttribute("customer", customerService.findCustomerById(id));
//        System.out.println("New name on customer registered " + id + " " + newName);
//        customerService.editCustomer(id, newName);
//        customerDao.save(customer);
//        return "edit-customer";
//    }
//
//    @DeleteMapping("/remove/{id}")
//    public String deleteCustomer(Model model, @PathVariable Long customerId) {
//        model.addAttribute("customer", customerId);
//        System.out.println("deleting customer " + customerId + " ");
//        customerService.deleteCustomer(customerId);
//        return "delete";
//    }

//    @GetMapping("customer/name/{name}")
//    @ResponseBody
//    public String findCustomerByName(Model model, @PathVariable String name) {
//        Customer customer = customerService.findByName(name);
//        model.addAttribute("customer", customerService.f);
//        return "customer-details";
//    }

    //    @PostMapping("/register")
//    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
//        try {
//            customerService.createAccount(customer);
//            return "redirect:/customers/login";
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//            return "registration";
//        }
//    }

    @GetMapping("/customer/id/{id}")
    @ResponseBody
    public String findCustomerById(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findCustomerById(id));
        return "customers";
    }
}
