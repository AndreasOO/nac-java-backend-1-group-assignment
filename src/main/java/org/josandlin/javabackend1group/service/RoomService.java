package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RoomService {

    List<RoomDTO> getAllRooms();

    List<Integer> getCapacityOptions();

    RoomDTO getRoomById(Long id);

    List<RoomDTO> getAvailableRoomsBetweenDatesWithinCapacity(LocalDate startDate, LocalDate endDate, int quests);
}
