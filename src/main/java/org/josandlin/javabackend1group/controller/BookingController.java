package org.josandlin.javabackend1group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("bookings")
public class BookingController {

    @GetMapping("rooms/{startDate}-{endDate}")
    public String showAvailableRooms(Model model, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return "rooms";
    }

    @GetMapping("rooms/{id}")
    public String showSelectedRoom(Model model) {

        return "selectedRoom";
    }

    @PostMapping("rooms/{id}")
    public String bookRoom(Model model, @PathVariable String id) {
        System.out.println("booked room " + id);
        return "selectedRoom";
    }

    @PutMapping("rooms/{id}")
    public String editBookedRoom(Model model, @PathVariable String id) {
        System.out.println("edited room " + id);
        return "selectedRoom";
    }

    @DeleteMapping("rooms/{id}")
    public String cancelBookedRoom(Model model, @PathVariable String id) {
        System.out.println("cancelled room " + id);
        return "selectedRoom";
    }



}
