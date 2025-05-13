package org.josandlin.javabackend1group.repositories;

import org.josandlin.javabackend1group.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select ")
    public List<Room> findAvailableRooms();
}
