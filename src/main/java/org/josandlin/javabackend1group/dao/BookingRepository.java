package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
