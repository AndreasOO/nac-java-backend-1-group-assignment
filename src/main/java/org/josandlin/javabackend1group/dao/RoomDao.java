package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<Room, Long> {

}
