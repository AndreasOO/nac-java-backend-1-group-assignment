package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.entity.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RoomService {

    List<Room> getAllRooms();

    int getRoomMaxCapacity();

    Room getRoomById(Long id);

    List<Room> getAvailableRoomsBetweenDatesWithinCapacity(LocalDate startDate, LocalDate endDate, int quests);
}
