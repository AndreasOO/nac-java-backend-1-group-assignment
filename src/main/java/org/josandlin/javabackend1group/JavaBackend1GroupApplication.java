package org.josandlin.javabackend1group;

import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class JavaBackend1GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBackend1GroupApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookingRepository bookingRepository, CustomerRepository customerRepository,
                                  RoomTypeRepository roomTypeRepository, RoomRepository roomRepository,
                                  BookedRoomRepository bookedRoomRepository, ExtraTypeRepository extraTypeRepository,
                                  AddedExtraRepository addedExtraRepository) {
        return (args) -> {

            Customer ola = new Customer("Ola");
            Customer milly =  new Customer("Milly");
            Customer andreas = new Customer("Andreas");
            Customer linn = new Customer("Linn");
            Customer josefin = new Customer("Josefin");

            customerRepository.save(ola);
            customerRepository.save(milly);
            customerRepository.save(andreas);
            customerRepository.save(linn);
            customerRepository.save(josefin);

            Booking olasBooking = new Booking(ola, LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 20));
            Booking millysBooking = new Booking(milly, LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 22));
            Booking andreasBooking = new Booking(andreas, LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));
            Booking linnsBooking = new Booking(linn, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
            Booking josefinsBooking = new Booking(josefin, LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

            bookingRepository.save(olasBooking);
            bookingRepository.save(millysBooking);
            bookingRepository.save(andreasBooking);
            bookingRepository.save(linnsBooking);
            bookingRepository.save(josefinsBooking);

            RoomType singleRoom = new RoomType("Single room", 1000);
            RoomType doubleRoom = new RoomType("Double room", 2000);
            RoomType twinRoom = new RoomType("Twin room", 1900);
            RoomType quadRoom = new RoomType("Quad room", 3200);

            roomTypeRepository.save(singleRoom);
            roomTypeRepository.save(doubleRoom);
            roomTypeRepository.save(twinRoom);
            roomTypeRepository.save(quadRoom);

            Room roomOne = new Room("Sea view room", 3, 3, doubleRoom);
            Room roomTwo = new Room("Dumpster room", 2, 1, twinRoom);
            Room roomThree = new Room("Honeymoon suite", 2, 4, doubleRoom);
            Room roomFour = new Room("Nice room", 2, 2, singleRoom);
            Room roomFive = new Room("Ok room", 4, 0, quadRoom);

            roomRepository.save(roomOne);
            roomRepository.save(roomTwo);
            roomRepository.save(roomThree);
            roomRepository.save(roomFour);
            roomRepository.save(roomFive);

            BookedRoom firstRoomInOlasBooking = new BookedRoom(roomTwo, olasBooking);
            BookedRoom secondRoomInOlasBooking = new BookedRoom(roomFive, olasBooking);
            BookedRoom roomInMillysBooking = new BookedRoom(roomOne, millysBooking);
            BookedRoom roomInAndreasBooking = new BookedRoom(roomOne, andreasBooking);
            BookedRoom roomInLinnsBooking = new BookedRoom(roomThree, linnsBooking);
            BookedRoom roomInJosefinsBooking = new BookedRoom(roomFour, josefinsBooking);

            bookedRoomRepository.save(firstRoomInOlasBooking);
            bookedRoomRepository.save(secondRoomInOlasBooking);
            bookedRoomRepository.save(roomInMillysBooking);
            bookedRoomRepository.save(roomInAndreasBooking);
            bookedRoomRepository.save(roomInLinnsBooking);
            bookedRoomRepository.save(roomInJosefinsBooking);

            ExtraType extraBed = new ExtraType("single bed", 200);
            ExtraType extraSnacks = new ExtraType("snacks", 85);
            ExtraType extraTowels = new ExtraType("towels", 50);
            ExtraType extraPillows = new ExtraType("pillows", 50);
            ExtraType extraBabyCrib = new ExtraType("baby crib", 120);

            extraTypeRepository.save(extraBed);
            extraTypeRepository.save(extraSnacks);
            extraTypeRepository.save(extraTowels);
            extraTypeRepository.save(extraPillows);
            extraTypeRepository.save(extraBabyCrib);

            AddedExtra olasExtra = new AddedExtra(extraSnacks, firstRoomInOlasBooking, 5);
            AddedExtra andreasFirstExtra = new AddedExtra(extraBed, roomInAndreasBooking, 1);
            AddedExtra andreasSecondExtra = new AddedExtra(extraBabyCrib, roomInAndreasBooking, 1);
            AddedExtra millysExtra = new AddedExtra(extraPillows, roomInMillysBooking, 2);
            AddedExtra linnsExtra = new AddedExtra(extraSnacks, roomInLinnsBooking, 2);

            addedExtraRepository.save(olasExtra);
            addedExtraRepository.save(andreasFirstExtra);
            addedExtraRepository.save(andreasSecondExtra);
            addedExtraRepository.save(millysExtra);
            addedExtraRepository.save(linnsExtra);

        };
    }

}
