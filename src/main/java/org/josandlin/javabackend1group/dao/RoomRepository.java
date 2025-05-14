package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomById(Long id);

    List<Room> findAllByRoomType(RoomType roomType);

    List<Room> findAllByRoomCapacity(Long capacity);

    @Query("SELECT room FROM Room room WHERE room.id NOT IN (SELECT booked_room.room.id FROM BookedRoom booked_room)")
    List<Room> findAvailableRooms();

}
