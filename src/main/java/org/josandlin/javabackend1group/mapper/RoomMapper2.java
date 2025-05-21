package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dao.RoomTypeDao;
import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.dto.RoomDetailedDTO;
import org.josandlin.javabackend1group.entity.Room;
import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper2 {

    RoomTypeMapper roomTypeMapper;
    RoomTypeDao roomTypeDao;

    @Autowired
    public RoomMapper2(RoomTypeMapper roomTypeMapper, RoomTypeDao roomTypeDao) {
        this.roomTypeMapper = roomTypeMapper;
        this.roomTypeDao = roomTypeDao;
    }

    public RoomDetailedDTO toDTO(Room entity) {
        if (entity == null) {
            return null;
        }
        return new RoomDetailedDTO(entity.getId(),
                                    entity.getName(),
                                    entity.getMaxCapacity(),
                                    entity.getExtraBedsAvailable(),
                                    entity.getRoomType().getId(),
                                    entity.getRoomType().getName(),
                                    entity.getRoomType().getCostPerNight());
    }

    public Room toEntity(RoomDetailedDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Room(dto.getRoomId(),
                dto.getRoomName(),
                dto.getMaxCapacity(),
                dto.getExtraBedsAvailable(),
                roomTypeDao.findById(dto.getRoomTypeId()).orElseThrow(() -> new IllegalArgumentException("Room type not found")));
    }
}
