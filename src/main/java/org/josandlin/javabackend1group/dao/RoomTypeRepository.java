package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    Optional<RoomType> findById(Long id);

    Optional<RoomType> findByName(String name);
}
