package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomById(Long id);

    List<Room> findAllByRoomType(RoomType roomType);

    List<Room> findAllByRoomCapacity(int roomCapacity);

    @Query("SELECT room FROM Room room WHERE room.id NOT IN (SELECT booked_room.room.id FROM BookedRoom booked_room WHERE NOT (booked_room.booking.endDate < :startDate OR booked_room.booking.startDate > :endDate))")
    List<Room> findAvailableRoomsBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT booked_room.room FROM BookedRoom booked_room WHERE NOT (booked_room.booking.endDate < :startDate OR booked_room.booking.startDate >: endDate)")
    List<Room> findBookedRoomsBetween(LocalDate startDate, LocalDate endDate);

}
