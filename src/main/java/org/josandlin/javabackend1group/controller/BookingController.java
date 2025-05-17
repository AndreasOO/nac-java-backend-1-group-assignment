package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dao.BookingDao;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("bookings")
public class BookingController {

    private final BookingService bookingService;
    private final RoomDao roomDao;
    private final RoomTypeDao roomTypeDao;
    private final CustomerService customerService;

    @Autowired
    public BookingController(BookingService bookingService, RoomDao roomDao, RoomTypeDao roomTypeDao, CustomerService customerService) {
        this.bookingService = bookingService;
        this.roomDao = roomDao;
        this.roomTypeDao = roomTypeDao;
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public String showBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings"; // namnet på din Thymeleaf-sida
    }

    @GetMapping("/booking/{id}")
    public String showBooking(@PathVariable Long id, Model model) {
        List<BookedObject> bookedRooms = bookingService.getBookedRoomsByBookingId(id);
        model.addAttribute("bookedRooms", bookedRooms);
        return "booking";
    }

    @GetMapping("/booking-form")
    public String showBookingForm(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        List<Integer> capacityOptions = IntStream.rangeClosed(1, bookingService.getRoomMaxCapacity()).boxed().toList();

        model.addAttribute("customers", customers);
        model.addAttribute("capacityOptions", capacityOptions);
        return "booking-form";
    }

    @GetMapping("/rooms")
    public String createNewBooking(Model model, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Room> availableRooms = bookingService.getAvailableRoomsBetweenDates(startDate, endDate);
        model.addAttribute("rooms", availableRooms);
        return "rooms";
    }


//    @ResponseBody
//    @GetMapping("rooms/{startDate}/{endDate}")
//    public List<Room> showAvailableRooms(Model model, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
//        return bookingService.getAvailableRoomsBetweenDates(startDate, endDate);
//    }

    @ResponseBody
    @GetMapping("rooms/byType/{roomTypeId}")
    public List<Room> showRoomsAfterRoomType(Model model, @PathVariable Long roomTypeId) {
        RoomType roomType = roomTypeDao.findById(roomTypeId).orElse(null);
        return roomDao.findAllByRoomType(roomType);
    }

    @ResponseBody
    @GetMapping("rooms/byCapacity/{maxCapacity}")
    public List<Room> showRoomAfterMaxCapacity(Model model, @PathVariable int maxCapacity) {
        return roomDao.findAllByMaxCapacity(maxCapacity);
    }

    @ResponseBody
    @GetMapping("rooms/{id}")
    public Room showSelectedRoom(Model model, @PathVariable Long id) {
        return roomDao.findRoomById(id);
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
