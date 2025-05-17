package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dao.RoomDao;
import org.josandlin.javabackend1group.dao.RoomTypeDao;
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
        List<Booking> bookings = bookingService.getAllBookings();
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("bookings", bookings);
        model.addAttribute("customers", customers);
        return "bookings"; // namnet på din Thymeleaf-sida
    }

    @GetMapping("/booking/{id}")
    public String showBooking(@PathVariable Long id, Model model) {
        List<BookedObject> bookedRooms = bookingService.getBookedRoomsByBookingId(id);
        List<Integer> capacityOptions = IntStream.rangeClosed(1, bookingService.getRoomMaxCapacity()).boxed().toList();
        model.addAttribute("bookingId", id);
        model.addAttribute("capacityOptions", capacityOptions);
        model.addAttribute("bookedRooms", bookedRooms);
        return "booking";
    }

    @PostMapping("/add-room")
    public String addRoomToBooking(@RequestParam Long roomId, @RequestParam Long bookingId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        Room chosenRoom = bookingService.getRoomById(roomId);
        Booking currentBooking = bookingService.getBookingById(bookingId);
        BookedObject bookedObject = new BookedObject(chosenRoom, List.of(), currentBooking, startDate, endDate);
        bookingService.saveBookedObject(bookedObject);
        return "redirect:/bookings/booking/" + bookingId;
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam("customerId") Long customerId) {
        Customer customer = customerService.findById(customerId);
        Booking booking = new Booking(customer);
        booking = bookingService.createBooking(booking);
        return "redirect:/bookings/booking/" + booking.getId();
    }


    @GetMapping("/booking-form")
    public String showBookingForm(@RequestParam("customer") Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Booking newBooking = new Booking(customer);

        List<Integer> capacityOptions = IntStream.rangeClosed(1, bookingService.getRoomMaxCapacity()).boxed().toList();
        model.addAttribute("capacityOptions", capacityOptions);
        model.addAttribute("booking", newBooking);
        return "booking-form";
    }

    @GetMapping("/rooms")
    public String showRooms(Model model, @RequestParam("bookingId") Long bookingId, @RequestParam("questCount") int questCount, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<Room> availableRooms = bookingService.getAvailableRoomsWithinMaxCapacity(startDate, endDate, questCount);
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "rooms";
    }

//    @ResponseBody
//    @GetMapping("rooms/byType/{roomTypeId}")
//    public List<Room> showRoomsAfterRoomType(Model model, @PathVariable Long roomTypeId) {
//        RoomType roomType = roomTypeDao.findById(roomTypeId).orElse(null);
//        return roomDao.findAllByRoomType(roomType);
//    }
//
//    @ResponseBody
//    @GetMapping("rooms/byCapacity/{maxCapacity}")
//    public List<Room> showRoomAfterMaxCapacity(Model model, @PathVariable int maxCapacity) {
//        return roomDao.findAllByMaxCapacity(maxCapacity);
//    }
//
//    @ResponseBody
//    @GetMapping("rooms/{id}")
//    public Room showSelectedRoom(Model model, @PathVariable Long id) {
//        return roomDao.findRoomById(id);
//    }
//
//    @PostMapping("rooms/{id}")
//    public String bookRoom(Model model, @PathVariable String id) {
//        System.out.println("booked room " + id);
//        return "selectedRoom";
//    }
//
//    @PutMapping("rooms/{id}")
//    public String editBookedRoom(Model model, @PathVariable String id) {
//        System.out.println("edited room " + id);
//        return "selectedRoom";
//    }
//
//    @DeleteMapping("rooms/{id}")
//    public String cancelBookedRoom(Model model, @PathVariable String id) {
//        System.out.println("cancelled room " + id);
//        return "selectedRoom";
//    }

}
