package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {

}
