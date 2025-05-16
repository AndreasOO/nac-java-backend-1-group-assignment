package org.josandlin.javabackend1group;

import org.josandlin.javabackend1group.dao.*;
import org.josandlin.javabackend1group.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class JavaBackend1GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBackend1GroupApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookingDao bookingDao, CustomerDao customerDao,
                                  RoomTypeDao roomTypeDao, RoomDao roomDao,
                                  BookedObjectDao bookedRoomDao, ExtraTypeDao extraTypeDao,
                                  AddedExtraDao addedExtraDao) {
        return (args) -> {

            Customer ola = new Customer("Ola");
            Customer milly =  new Customer("Milly");
            Customer andreas = new Customer("Andreas");
            Customer linn = new Customer("Linn");
            Customer josefin = new Customer("Josefin");
            Customer Sigge = new Customer("Sigge");

            customerDao.save(ola);
            customerDao.save(milly);
            customerDao.save(andreas);
            customerDao.save(linn);
            customerDao.save(josefin);
            customerDao.save(Sigge);

            Booking olasBooking = new Booking(ola);
            Booking millysBooking = new Booking(milly);
            Booking andreasBooking = new Booking(andreas);
            Booking linnsBooking = new Booking(linn);
            Booking josefinsBooking = new Booking(josefin);

            bookingDao.save(olasBooking);
            bookingDao.save(millysBooking);
            bookingDao.save(andreasBooking);
            bookingDao.save(linnsBooking);
            bookingDao.save(josefinsBooking);

            RoomType singleRoom = new RoomType("Single room", 1000);
            RoomType doubleRoom = new RoomType("Double room", 2000);
            RoomType twinRoom = new RoomType("Twin room", 1900);
            RoomType quadRoom = new RoomType("Quad room", 3200);

            roomTypeDao.save(singleRoom);
            roomTypeDao.save(doubleRoom);
            roomTypeDao.save(twinRoom);
            roomTypeDao.save(quadRoom);

            ExtraType extraBed = new ExtraType("single bed", 200);
            ExtraType extraSnacks = new ExtraType("snacks", 85);
            ExtraType extraTowels = new ExtraType("towels", 50);
            ExtraType extraPillows = new ExtraType("pillows", 50);
            ExtraType extraBabyCrib = new ExtraType("baby crib", 120);

            extraTypeDao.save(extraBed);
            extraTypeDao.save(extraSnacks);
            extraTypeDao.save(extraTowels);
            extraTypeDao.save(extraPillows);
            extraTypeDao.save(extraBabyCrib);

            AddedExtra olasExtra = new AddedExtra(extraSnacks);
            AddedExtra andreasFirstExtra = new AddedExtra(extraBed);
            AddedExtra andreasSecondExtra = new AddedExtra(extraBabyCrib);
            AddedExtra millysExtra = new AddedExtra(extraPillows);
            AddedExtra linnsExtra = new AddedExtra(extraSnacks);

            addedExtraDao.save(olasExtra);
            addedExtraDao.save(andreasFirstExtra);
            addedExtraDao.save(andreasSecondExtra);
            addedExtraDao.save(millysExtra);
            addedExtraDao.save(linnsExtra);

            Room roomOne = new Room("Sea view room", 3, 3, doubleRoom);
            Room roomTwo = new Room("Dumpster room", 2, 1, twinRoom);
            Room roomThree = new Room("Honeymoon suite", 2, 4, doubleRoom);
            Room roomFour = new Room("Nice room", 2, 2, singleRoom);
            Room roomFive = new Room("Ok room", 4, 0, quadRoom);
            Room roomSix = new Room("Unbooked room", 1, 1, singleRoom);

            roomDao.save(roomOne);
            roomDao.save(roomTwo);
            roomDao.save(roomThree);
            roomDao.save(roomFour);
            roomDao.save(roomFive);
            roomDao.save(roomSix);

            BookedObject firstRoomInOlasBooking = new BookedObject(roomTwo, List.of(olasExtra), olasBooking, LocalDate.of(2025, 5, 14), LocalDate.of(2025, 5, 20));
            BookedObject secondRoomInOlasBooking = new BookedObject(roomFive, List.of(), olasBooking, LocalDate.of(2025, 6, 2), LocalDate.of(2025, 6, 17));
            BookedObject roomInMillysBooking = new BookedObject(roomOne, List.of(millysExtra), millysBooking, LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 22));
            BookedObject roomInAndreasBooking = new BookedObject(roomOne, List.of(andreasFirstExtra, andreasSecondExtra), andreasBooking, LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 18));
            BookedObject roomInLinnsBooking = new BookedObject(roomThree, List.of(linnsExtra), linnsBooking, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 8));
            BookedObject roomInJosefinsBooking = new BookedObject(roomFour, List.of(), josefinsBooking, LocalDate.of(2025, 5, 29), LocalDate.of(2025, 6, 2));

//            firstRoomInOlasBooking.setExtras(List.of(olasExtra));
//            roomInMillysBooking.setExtras(List.of(millysExtra));
//            roomInAndreasBooking.setExtras(List.of(andreasFirstExtra, andreasSecondExtra));
//            roomInLinnsBooking.setExtras(List.of(linnsExtra));

            bookedRoomDao.save(firstRoomInOlasBooking);
            bookedRoomDao.save(secondRoomInOlasBooking);
            bookedRoomDao.save(roomInMillysBooking);
            bookedRoomDao.save(roomInAndreasBooking);
            bookedRoomDao.save(roomInLinnsBooking);
            bookedRoomDao.save(roomInJosefinsBooking);
        };
    }

}
