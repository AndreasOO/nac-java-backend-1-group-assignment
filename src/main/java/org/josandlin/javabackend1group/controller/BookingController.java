package org.josandlin.javabackend1group.controller;

import org.josandlin.javabackend1group.dto.BookedObjectDTO;
import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.*;
import org.josandlin.javabackend1group.service.BookingService;
import org.josandlin.javabackend1group.service.CustomerService;
import org.josandlin.javabackend1group.service.RoomService;
import org.josandlin.javabackend1group.util.RoomSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;import java.util.List;
import java.util.stream.IntStream;


@Controller
@RequestMapping("bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    @Autowired
    public BookingController(BookingService bookingService, CustomerService customerService, RoomService roomService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public String showBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("customers", bookingService.getAllCustomers());
        return "bookings";
    }

    @GetMapping("/booking/add-room")
    public String showAvailableRooms(Model model, RoomSearch roomSearch) {
        model.addAttribute("roomSearch", roomSearch);
        model.addAttribute("availableRooms", roomService.getAvailableRoomsBetweenDatesWithinCapacity(roomSearch.getStartDate(),
                                                                                                        roomSearch.getEndDate(),
                                                                                                        roomSearch.getGuestCount()));
        return "available-rooms";
    }

    @GetMapping("/booking")
    public String showBooking(Model model, @RequestParam Long bookingId) {
        RoomSearch roomSearch = new RoomSearch();
        roomSearch.setUpdate(false);

        model.addAttribute("roomSearch", roomSearch);

        model.addAttribute("customer", bookingService.getCustomerByBookingId(bookingId));
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("capacityOptions", roomService.getCapacityOptions());
        model.addAttribute("bookedRooms", bookingService.getBookedRoomsByBookingId(bookingId));
        return "booking";
    }

    @GetMapping("/booking/booked-room")
    public String showBookedRoom(Model model, @RequestParam Long bookedObjectId){

        model.addAttribute("roomSearch", new RoomSearch());

        model.addAttribute("bookedObject", bookingService.getBookedObjectById(bookedObjectId));
        model.addAttribute("extraChoices", bookingService.getExtraOptionsAvailable(bookedObjectId));
        model.addAttribute("capacityOptions", roomService.getCapacityOptions());
        return "booked-room";
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam("customerId") Long customerId) {
        BookingDTO newBooking = bookingService.createBooking(customerId);
        return "redirect:/bookings/booking?bookingId=" + newBooking.getId();
    }

    @PostMapping("/booking/add-room")
    public String addRoomToBooking(@RequestParam Long roomId,
                                   @RequestParam Long bookingId,
                                   @RequestParam LocalDate startDate,
                                   @RequestParam LocalDate endDate){

        BookedObjectDTO bookedObject = bookingService.saveBookedObject(roomService.getRoomById(roomId), bookingId, startDate, endDate);
        return "redirect:/bookings/booking?bookingId=" + bookedObject.getBooking().getId();
    }

    @PostMapping("/booking/delete-extra")
    public String deleteExtraFromBookedRoom(@RequestParam Long bookedObjectId,
                                            @RequestParam Long extraId) {

        bookingService.deleteExtraFromBookedObjectById(extraId);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObjectId;
    }

    @PostMapping("/booking/add-extra")
    public String addExtraToBookedRoom(@RequestParam Long bookedObjectId,
                                       @RequestParam Long extraTypeId) {

        BookedObjectDTO bookedObject = bookingService.addExtraToBookedObject(bookedObjectId, extraTypeId);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObject.getId();
    }

    @PostMapping("/booking/delete-room")
    public String deleteRoom(@RequestParam Long bookedObjectId,
                             @RequestParam Long bookingId) {

        bookingService.deleteBookedObject(bookedObjectId);
        return "redirect:/bookings/booking?bookingId=" + bookingId;
    }

    @PostMapping("/booking/edit-room")
    public String editRoom(@RequestParam Long bookedObjectId,
                           @RequestParam Long bookingId,
                           @RequestParam Long roomId,
                           @RequestParam LocalDate startDate,
                           @RequestParam LocalDate endDate){

        BookedObjectDTO bookedObject = bookingService.editBookedObject(bookedObjectId, roomId, startDate, endDate);
        return "redirect:/bookings/booking/booked-room?bookedObjectId=" + bookedObject.getId();
    }
}
