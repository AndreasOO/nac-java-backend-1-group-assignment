package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.BookedObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepository extends JpaRepository<BookedObject, Long> {

}
