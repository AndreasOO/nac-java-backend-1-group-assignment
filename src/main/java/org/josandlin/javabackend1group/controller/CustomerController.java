package org.josandlin.javabackend1group.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService, View error) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("sucessEdit");
        return "customers";
    }

    @GetMapping("/register")
    public String showCustomerRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "registration";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") @Valid CustomerDTO customerDTO,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registration";
        }
        try {
            customerService.registerCustomer(customerDTO);
            redirectAttributes.addFlashAttribute("success", "Customer registered successfully!");
            return "redirect:/customers/all";
        } catch (ValidationException e) {
            e.getMessage();
            return "registration";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CustomerDTO customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "edit";
    }
    @PostMapping("/edit")       // visar inga felmeddelanden eller liknande men den funkar.
    public String editCustomer(@ModelAttribute("customer") @Valid CustomerDTO customerDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editModalId", customerDTO.getId());
            return "redirect:/customers/all";
        }
        try {
            customerService.editCustomer(customerDTO);
            redirectAttributes.addFlashAttribute("successEdit", "Customer name updated successfully!"); //visas ej
            return "redirect:/customers/all";
        } catch (ValidationException e) {
            model.addAttribute("editModalId", customerDTO.getId());
            return "customers";
        }
    }

    @DeleteMapping("/delete/{id}")    //Visar felmeddelanden och sucess
    public String removeCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
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
}
