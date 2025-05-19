package org.josandlin.javabackend1group.controller;

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

    // Snygga upp alla endpoints
    // Använder mig än så länge av RequestParams, snyggare med PathVariable?

    @GetMapping("/all")
    public String showBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("bookings", bookings);
        model.addAttribute("customers", customers);
        return "bookings";
    }

    @GetMapping("/booking")
    public String showBooking(@RequestParam Long bookingId, Model model) {
        List<BookedObject> bookedRooms = bookingService.getBookedRoomsByBookingId(bookingId);
        List<Integer> capacityOptions = IntStream.rangeClosed(1, bookingService.getRoomMaxCapacity()).boxed().toList();
        model.addAttribute("customer", bookingService.getCustomerByBookingId(bookingId));
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("capacityOptions", capacityOptions);
        model.addAttribute("bookedRooms", bookedRooms);
        return "booking";
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam("customerId") Long customerId) {
        Customer customer = customerService.findById(customerId);
        Booking booking = new Booking(customer);
        booking = bookingService.createBooking(booking);
        return "redirect:/bookings/booking?bookingId=" + booking.getId();
    }

    @GetMapping("/booking/add-room")
    public String showRooms(Model model, @RequestParam("bookingId") Long bookingId, @RequestParam("questCount") int questCount, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<Room> availableRooms = bookingService.getAvailableRoomsWithinMaxCapacity(startDate, endDate, questCount);
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "available-rooms";
    }

    @PostMapping("/booking/add-room")
    public String addRoomToBooking(@RequestParam Long roomId, @RequestParam Long bookingId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        Room chosenRoom = bookingService.getRoomById(roomId);
        Booking currentBooking = bookingService.getBookingById(bookingId);
        BookedObject bookedObject = new BookedObject(chosenRoom, List.of(), currentBooking, startDate, endDate);
        bookingService.saveBookedObject(bookedObject);
        return "redirect:/bookings/booking?bookingId=" + bookingId;
    }

    @GetMapping("/booking/booked-room")
    public String showBookedRoom(Model model, @RequestParam Long bookedObjectId){
        BookedObject bookedObject = bookingService.getBookedObjectById(bookedObjectId);
        List<ExtraType> extraChoices = bookingService.getAllExtraChoicesAvailable(bookedObjectId);
        model.addAttribute("bookedObject", bookedObject);
        model.addAttribute("extraChoices", extraChoices);
        return "booked-room";
    }

    @PostMapping("/booking/delete-extra")
    public String deleteExtraFromBookedRoom(Model model, @RequestParam Long bookedObjectId, @RequestParam Long extraId) {
        bookingService.deleteExtraFromBookedObjectById(extraId);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObjectId;
    }

    @PostMapping("/booking/add-extra")
    public String addExtraToBookedRoom(Model model, @RequestParam Long bookedObjectId, @RequestParam Long extraTypeId) {
        bookingService.addExtraToBookedObject(bookedObjectId, extraTypeId);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObjectId;
    }

    @PostMapping("/booking/delete-room")
    public String deleteRoom(Model model, @RequestParam Long bookedObjectId, @RequestParam Long bookingId) {
        bookingService.removeBookedObject(bookedObjectId);
        return "redirect:/bookings/booking?bookingId=" + bookingId;
    }


}
