package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dao.RoomRepository;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {

    @Autowired
    RoomRepository roomRepository;

    @GetMapping("rooms/{startDate}/{endDate}")
    public List<Room> showAvailableRooms(Model model, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return roomRepository.findAvailableRoomsBetween(startDate, endDate);
    }

    @GetMapping("rooms/{id}")
    public Room showSelectedRoom(Model model, @PathVariable Long id) {
        return roomRepository.findRoomById(id);
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
