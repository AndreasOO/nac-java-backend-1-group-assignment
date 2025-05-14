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


}
