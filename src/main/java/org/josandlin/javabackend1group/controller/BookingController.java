package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dto.BookedObjectDTO;
import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.*;
import org.josandlin.javabackend1group.service.BookingService;
import org.josandlin.javabackend1group.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;

    @Autowired
    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public String showBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("customers", bookingService.getAllCustomers());
        return "bookings";
    }

    @GetMapping("/booking/add-room")
    public String showAvailableRooms(Model model, @RequestParam("bookingId") Long bookingId, @RequestParam("guestCount") int guestCount, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        model.addAttribute("guests", guestCount);
        model.addAttribute("rooms", bookingService.getAvailableRoomsBetweenDatesWithinCapacity(startDate, endDate, guestCount));
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "available-rooms";
    }

    @GetMapping("/booking")
    public String showBooking(@RequestParam Long bookingId, Model model) {
        model.addAttribute("customer", bookingService.getCustomerByBookingId(bookingId));
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("capacityOptions", bookingService.getRoomSizeOptions());
        model.addAttribute("bookedRooms", bookingService.getBookedRoomsByBookingId(bookingId));
        return "booking";
    }

    @GetMapping("/booking/booked-room")
    public String showBookedRoom(Model model, @RequestParam Long bookedObjectId){
        model.addAttribute("bookedObject", bookingService.getBookedObjectById(bookedObjectId));
        model.addAttribute("extraChoices", bookingService.getAllExtraChoicesAvailable(bookedObjectId));
        return "booked-room";
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam("customerId") Long customerId) {
        BookingDTO newBooking = bookingService.createBooking(customerId);
        return "redirect:/bookings/booking?bookingId=" + newBooking.getId();
    }

    @PostMapping("/booking/add-room")
    public String addRoomToBooking(@RequestParam Long roomId, @RequestParam Long bookingId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        bookingService.saveBookedObject(roomId, bookingId, startDate, endDate);
        return "redirect:/bookings/booking?bookingId=" + bookingId;
    }

    @PostMapping("/booking/delete-extra")
    public String deleteExtraFromBookedRoom(@RequestParam Long bookedObjectId, @RequestParam Long extraId) {
        bookingService.deleteExtraFromBookedObjectById(extraId);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObjectId;
    }

    @PostMapping("/booking/add-extra")
    public String addExtraToBookedRoom(@RequestParam Long bookedObjectId, @RequestParam Long extraTypeId) {
        bookingService.addExtraToBookedObject(bookedObjectId, extraTypeId);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObjectId;
    }

    @PostMapping("/booking/delete-room")
    public String deleteRoom(@RequestParam Long bookedObjectId, @RequestParam Long bookingId) {
        bookingService.removeBookedObject(bookedObjectId);
        return "redirect:/bookings/booking?bookingId=" + bookingId;
    }
}
