package org.josandlin.javabackend1group.service;

import org.josandlin.javabackend1group.dao.BookedObjectDao;
import org.josandlin.javabackend1group.dao.RoomDao;
import org.josandlin.javabackend1group.entity.BookedObject;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService{

    private final RoomDao roomDao;
    private final BookedObjectDao bookedObjectDao;

    public RoomServiceImpl(RoomDao roomDao, BookedObjectDao bookedObjectDao) {
        this.roomDao = roomDao;
        this.bookedObjectDao = bookedObjectDao;
    }

    @Override
    public List<Room> getAllRooms(){
        return roomDao.findAll();
    }

    @Override
    public int getRoomMaxCapacity(){
        return roomDao.findAll().stream().map(Room::getMaxCapacity).max(Comparator.naturalOrder()).orElse(1);
    }

    @Override
    public Room getRoomById(Long id){
        return roomDao.findById(id).orElseThrow(()-> new IllegalArgumentException("Can't find room"));
    }

    @Override
    public List<Room> getAvailableRoomsBetweenDatesWithinCapacity(LocalDate startDate, LocalDate endDate, int guests){
        Set<Room> roomsBookedBetweenDates = bookedObjectDao.findAll()
                .stream().filter(bookedRoom -> !bookedRoom.getEndDate().isBefore(startDate)
                        && !bookedRoom.getStartDate().isAfter(endDate))
                .map(BookedObject::getRoom).collect(Collectors.toSet());

        return roomDao.findAll().stream()
                .filter(room -> !roomsBookedBetweenDates.contains(room)).filter(room -> room.getMaxCapacity() >= guests).toList();
    }
}
