package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends JpaRepository<Room, Long> {

    Room findRoomById(Long id);

    List<Room> findAll();

    List<Room> findAllByRoomType(RoomType roomType);

    List<Room> findAllByMaxCapacity(int roomCapacity);

    // Välj de rum där rummet inte återfinns bland bokade rum, där datumen överlappar med de datum användaren skriver in
    @Query("SELECT room FROM Room room where room.id NOT IN(SELECT booked_room.room.id FROM BookedObject booked_room WHERE booked_room.startDate <= :endDate AND booked_room.endDate >= :startDate)")
    List<Room> findAvailableRoomsBetween(LocalDate startDate, LocalDate endDate);
}
