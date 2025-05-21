package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.dto.RoomDetailedDTO;
import org.josandlin.javabackend1group.entity.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RoomService {

    List<RoomDetailedDTO> getAllRooms();

    List<Integer> getCapacityOptions();

    RoomDetailedDTO getRoomById(Long id);

    List<RoomDetailedDTO> getAvailableRoomsBetweenDatesWithinCapacity(LocalDate startDate, LocalDate endDate, int quests);
}
