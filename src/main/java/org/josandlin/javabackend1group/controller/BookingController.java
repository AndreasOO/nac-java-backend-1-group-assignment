package org.josandlin.javabackend1group.controller;

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

    @GetMapping("/booking/{bookingId}")
    public String showBooking(@PathVariable Long bookingId, Model model) {
        model.addAttribute("roomSearch", new RoomSearchInputUtil(false, bookingId));
        model.addAttribute("capacityOptions", roomService.getCapacityOptions());
        model.addAttribute("booking", bookingService.getBookingById(bookingId));
        model.addAttribute("bookedRooms", bookingService.getBookedRoomsByBookingId(bookingId));
        return "booking";
    }

    // metod som skickar ett RoomSearchInputUtil för att söka på rum att ändra, försöker nå showAvailableRooms
    @GetMapping("/booking/{bookingId}/booked-room/{bookedObjectId}")
    public String showBookedRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, Model model){
        model.addAttribute("roomSearch", new RoomSearchInputUtil(true, bookingId, bookedObjectId));
        model.addAttribute("bookedObject", bookingService.getBookedObjectById(bookedObjectId));
        model.addAttribute("extraChoices", bookingService.getExtraOptionsAvailable(bookedObjectId));
        model.addAttribute("capacityOptions", roomService.getCapacityOptions());
        return "booked-room";
    }

    // visar tillgängliga rum, går vidare till antingen add-room eller edit-room
    @GetMapping("/booking/available-rooms")
    public String showAvailableRooms(@ModelAttribute RoomSearchInputUtil roomSearch, Model model) {
        BookedObjectInputUtil chosenRoom = new BookedObjectInputUtil(roomSearch.getBookingId(), roomSearch.getStartDate(), roomSearch.getEndDate());

        if (roomSearch.isUpdate()){
            chosenRoom.setBookedObjectId(roomSearch.getBookedObjectId());
        }

        model.addAttribute("chosenRoom", chosenRoom);
        model.addAttribute("roomSearch", roomSearch);
        model.addAttribute("rooms", roomService.getAvailableRoomsBetweenDatesWithinCapacity(roomSearch.getStartDate(), roomSearch.getEndDate(), roomSearch.getQuestCount()));

        return "available-rooms";
    }

    // nås via booking-sida
    @PostMapping("/booking/add-room")
    public String addRoomToBooking(@ModelAttribute BookedObjectInputUtil chosenRoom){

        BookedObjectDTO bookedObject = bookingService.saveBookedObject(roomService.getRoomById(chosenRoom.getRoomId()),
                                                                                                chosenRoom.getBookingId(),
                                                                                                chosenRoom.getStartDate(),
                                                                                                chosenRoom.getEndDate());
        return "redirect:/bookings/booking/" + bookedObject.getBooking().getId();
    }

    // nås via booked-room sida
    @PostMapping("/booking/edit-room")
    public String editRoom(@ModelAttribute BookedObjectInputUtil chosenRoom){

        BookedObjectDTO bookedObject = bookingService.editBookedObject(chosenRoom.getBookedObjectId(),
                                                                        chosenRoom.getRoomId(),
                                                                        chosenRoom.getStartDate(),
                                                                        chosenRoom.getEndDate());

        return "redirect:/bookings/booking/" + bookedObject.getBooking().getId() + "/booked-room/" + bookedObject.getId();
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam Long customerId) {
        BookingDTO newBooking = bookingService.createBooking(customerId);
        return "redirect:/bookings/booking/" + newBooking.getId();
    }

    @PostMapping("/booking/{bookingId}/booked-room/{bookedObjectId}/delete-extra/{extraId}")
    public String deleteExtraFromBookedRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, @PathVariable Long extraId) {
        bookingService.deleteExtraFromBookedObjectById(extraId);
        return "redirect:/bookings/booking/" + bookingId + "/booked-room/" + bookedObjectId;
    }

    @PostMapping("/booking/{bookingId}/booked-room/{bookedObjectId}/add-extra")
    public String addExtraToBookedRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId, @RequestParam Long extraTypeId) {
        BookedObjectDTO bookedObject = bookingService.addExtraToBookedObject(bookedObjectId, extraTypeId);
        return "redirect:/bookings/booking/" + bookedObject.getBooking().getId() + "/booked-room/" + bookedObject.getId();
    }

    @PostMapping("/booking/{bookingId}/booked-room/{bookedObjectId}/delete-room")
    public String deleteRoom(@PathVariable Long bookingId, @PathVariable Long bookedObjectId) {
        bookingService.deleteBookedObject(bookedObjectId);
        return "redirect:/bookings/booking/" + bookingId;
    }





//    @PostMapping("/booking")
//    public String createBooking(@RequestParam("customerId") Long customerId) {
//        BookingDTO newBooking = bookingService.createBooking(customerId);
//        return "redirect:/bookings/booking?bookingId=" + newBooking.getId();
//    }
//
//    @PostMapping("/booking/delete-extra")
//    public String deleteExtraFromBookedRoom(@RequestParam Long bookedObjectId,
//                                            @RequestParam Long extraId) {
//
//        bookingService.deleteExtraFromBookedObjectById(extraId);
//        return "redirect:/bookings/booking/" + bookedObject.getBooking().getId() + "/booked-room/" + bookedObject.getId();
//    }
//
//    @PostMapping("/booking/add-extra")
//    public String addExtraToBookedRoom(@RequestParam Long bookedObjectId,
//                                       @RequestParam Long extraTypeId) {
//
//        BookedObjectDTO bookedObject = bookingService.addExtraToBookedObject(bookedObjectId, extraTypeId);
//        return "redirect:/bookings/booking/" + bookedObject.getBooking().getId() + "/booked-room/" + bookedObject.getId();
//    }
//
//    @PostMapping("/booking/delete-room")
//    public String deleteRoom(@RequestParam Long bookedObjectId,
//                             @RequestParam Long bookingId) {
//
//        bookingService.deleteBookedObject(bookedObjectId);
//        return "redirect:/bookings/booking/" + bookingId;
//    }
}
