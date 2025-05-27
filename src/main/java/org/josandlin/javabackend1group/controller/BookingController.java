package org.josandlin.javabackend1group.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.josandlin.javabackend1group.dto.BookedObjectDTO;
import org.josandlin.javabackend1group.dto.BookingDTO;
import org.josandlin.javabackend1group.service.BookingService;
import org.josandlin.javabackend1group.service.CustomerService;
import org.josandlin.javabackend1group.service.RoomService;
import org.josandlin.javabackend1group.util.BookedObjectInputUtil;
import org.josandlin.javabackend1group.util.RoomSearchInputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;


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
    public String showBookings(Model model, RedirectAttributes redirectAttributes) {
        try{
            model.addAttribute("bookings", bookingService.getAllBookings());
            model.addAttribute("customers", customerService.getAllCustomers());
            return "bookings";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred: " +  e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/booking/{bookingId}")
    public String showBooking(@PathVariable Long bookingId, Model model, RedirectAttributes redirectAttributes) {
        try{
            model.addAttribute("roomSearch", new RoomSearchInputUtil(false, bookingId));
            model.addAttribute("capacityOptions", roomService.getCapacityOptions());
            model.addAttribute("booking", bookingService.getBookingById(bookingId));
            model.addAttribute("bookedRooms", bookingService.getBookedRoomsByBookingId(bookingId));
            return "booking";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred trying to view booking: " + e.getMessage());
            return "redirect:/bookings/all";
        }
    }

    // metod som skickar ett RoomSearchInputUtil för att söka på rum att ändra, försöker nå showAvailableRooms
    @GetMapping("/booking/{bookingId}/booked-room/{bookedObjectId}")
    public String showBookedRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, Model model, RedirectAttributes redirectAttributes) {
        try{
            model.addAttribute("roomSearch", new RoomSearchInputUtil(true, bookingId, bookedObjectId));
            model.addAttribute("bookedObject", bookingService.getBookedObjectById(bookedObjectId));
            model.addAttribute("extraChoices", bookingService.getExtraOptionsAvailable(bookedObjectId));
            model.addAttribute("capacityOptions", roomService.getCapacityOptions());
            return "booked-room";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred trying to view room: " + e.getMessage());
            return "redirect:/bookings/all";
        }
    }

    // visar tillgängliga rum, går vidare till antingen add-room eller edit-room
    @GetMapping("/booking/available-rooms")
    public String showAvailableRooms(@ModelAttribute RoomSearchInputUtil roomSearch, Model model, RedirectAttributes redirectAttributes) {
        try{
            BookedObjectInputUtil chosenRoom = new BookedObjectInputUtil(roomSearch.getBookingId(), roomSearch.getStartDate(), roomSearch.getEndDate());

            if (roomSearch.isUpdate()){
                chosenRoom.setBookedObjectId(roomSearch.getBookedObjectId());
            }

            model.addAttribute("chosenRoom", chosenRoom);
            model.addAttribute("roomSearch", roomSearch);
            model.addAttribute("rooms", roomService.getAvailableRoomsBetweenDatesWithinCapacity(roomSearch.getStartDate(), roomSearch.getEndDate(), roomSearch.getGuestCount()));

            return "available-rooms";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred searching for available rooms: " + e.getMessage());
            return "redirect:/bookings/all";
        }
    }

    // nås via available-rooms
    @PostMapping("/booking/add-room")
    public String addRoomToBooking(@ModelAttribute BookedObjectInputUtil chosenRoom, RedirectAttributes redirectAttributes) {
        try{
            BookedObjectDTO bookedObject = bookingService.saveBookedObject(roomService.getRoomById(chosenRoom.getRoomId()),
                                                                                                    chosenRoom.getBookingId(),
                                                                                                    chosenRoom.getStartDate(),
                                                                                                    chosenRoom.getEndDate());
            return "redirect:/bookings/booking/" + bookedObject.getBooking().getId();
        }
        catch (ConstraintViolationException e){
            String error = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            redirectAttributes.addFlashAttribute("error", error);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred when adding room to booking: " + e.getMessage());
        }
        return "redirect:/bookings/booking/" + chosenRoom.getBookingId();
    }

    // nås via available-rooms
    @PostMapping("/booking/edit-room")
    public String editRoom(@ModelAttribute BookedObjectInputUtil chosenRoom, RedirectAttributes redirectAttributes){
        try{
            BookedObjectDTO bookedObject = bookingService.editBookedObject(chosenRoom.getBookedObjectId(),
                                                                            chosenRoom.getRoomId(),
                                                                            chosenRoom.getStartDate(),
                                                                            chosenRoom.getEndDate());
            return "redirect:/bookings/booking/" + bookedObject.getBooking().getId() + "/booked-room/" + bookedObject.getId();
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred when editing this room: " + e.getMessage());
            return "redirect:/bookings/booking/" + chosenRoom.getBookingId();
        }
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam Long customerId, RedirectAttributes redirectAttributes) {
        try{
            BookingDTO newBooking = bookingService.createBooking(customerId);
            return "redirect:/bookings/booking/" + newBooking.getId();
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
            return "redirect:/bookings/all";
        }
    }

    // kommer inte åt exception, fångas i boolean
    @DeleteMapping("/booking/{bookingId}/booked-room/{bookedObjectId}/delete-extra/{extraId}")
    public String deleteExtraFromBookedRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, @PathVariable Long extraId, RedirectAttributes redirectAttributes) {
        try{
            boolean deleted = bookingService.deleteExtraFromBookedObjectById(extraId);
            if(!deleted){
                redirectAttributes.addFlashAttribute("error", "Could not delete extra");
            }
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred trying to delete extra item from room with id " + bookedObjectId + ": " + e.getMessage());
        }
        return "redirect:/bookings/booking/" + bookingId + "/booked-room/" + bookedObjectId;
    }

    @PostMapping("/booking/{bookingId}/booked-room/{bookedObjectId}/add-extra")
    public String addExtraToBookedRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, @RequestParam Long extraTypeId, RedirectAttributes redirectAttributes) {
        try{
            bookingService.addExtraToBookedObject(bookedObjectId, extraTypeId);
            return "redirect:/bookings/booking/" + bookingId + "/booked-room/" + bookedObjectId;
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while trying to add an extra item to room with id " + bookedObjectId + ": " + e.getMessage());
            return "redirect:/bookings/booking/" + bookingId;
        }
    }

    // kommer inte åt exception, fångas i boolean
    @DeleteMapping("/booking/{bookingId}/booked-room/{bookedObjectId}/delete-room")
    public String deleteRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, RedirectAttributes redirectAttributes) {
        try{
            boolean deleted = bookingService.deleteBookedObject(bookedObjectId);
            if(!deleted){
                redirectAttributes.addFlashAttribute("error", "Could not delete booked room");
            }
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred when deleting room: " + e.getMessage());
        }
        return "redirect:/bookings/booking/" + bookingId;
    }
}
