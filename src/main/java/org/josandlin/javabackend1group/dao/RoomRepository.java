package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

//    Room findRoomById(Long id);
//
//    List<Room> findAll();
//
//    List<Room> findAllByRoomType(RoomType roomType);
//
//    List<Room> findAllByMaxCapacity(int roomCapacity);
//
//    List<Room> findAvailableRoomsBetween(LocalDate startDate, LocalDate endDate);
//
//    List<Room> findBookedRoomsBetween(LocalDate startDate, LocalDate endDate);

}
