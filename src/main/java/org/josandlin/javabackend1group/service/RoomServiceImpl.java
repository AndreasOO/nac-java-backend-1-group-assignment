package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.BookedObjectDao;
import org.josandlin.javabackend1group.dao.RoomDao;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.mapper.BookedObjectMapper;
import org.josandlin.javabackend1group.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RoomServiceImpl implements RoomService{

    private final RoomDao roomDao;
    private final BookedObjectDao bookedObjectDao;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomServiceImpl(RoomDao roomDao, BookedObjectDao bookedObjectDao, RoomMapper roomMapper) {
        this.roomDao = roomDao;
        this.bookedObjectDao = bookedObjectDao;
        this.roomMapper = roomMapper;
    }

    @Override
    public List<RoomDTO> getAllRooms(){
        return roomDao.findAll().stream().map(roomMapper::toDTO).toList();
    }

    @Override
    public List<Integer> getCapacityOptions(){
        int max = roomDao.findAll().stream().map(Room::getMaxCapacity).max(Comparator.naturalOrder()).orElse(1);
        return IntStream.rangeClosed(1, max).boxed().toList();
    }

    @Override
    public RoomDTO getRoomById(Long id){
        return roomMapper.toDTO(roomDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found")));
    }

    @Override
    public List<RoomDTO> getAvailableRoomsBetweenDatesWithinCapacity(LocalDate startDate, LocalDate endDate, int guests){

                Set<Room> bookedRoomsBetweenDates = bookedObjectDao.findAll()
                .stream().filter(bookedObject -> bookedObject.getStartDate().isEqual(startDate) ||
                                                            (bookedObject.getStartDate().isAfter(startDate) && bookedObject.getStartDate().isBefore(endDate)) ||
                                                            (bookedObject.getStartDate().isBefore(startDate) && bookedObject.getEndDate().isAfter(startDate)))
                         .map(BookedObject::getRoom).collect(Collectors.toSet());

        return roomDao.findAll().stream()
                .filter(room -> !bookedRoomsBetweenDates.contains(room))
                .filter(room -> room.getMaxCapacity() >= guests)
                .map(roomMapper::toDTO).toList();
    }
}
