package org.josandlin.javabackend1group.dao;

import org.josandlin.javabackend1group.entity.Booking;
import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingDao extends JpaRepository<Booking, Long> {

}
