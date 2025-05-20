package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.RoomDTO;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    private final RoomTypeMapper roomTypeMapper;

    @Autowired
    public RoomMapper(RoomTypeMapper roomTypeMapper) {
        this.roomTypeMapper = roomTypeMapper;
    }

    public RoomDTO toDTO(Room entity) {
        if (entity == null) {
            return null;
        }
        return new RoomDTO(entity.getId(), entity.getName(), entity.getMaxCapacity(), entity.getExtraBedsAvailable(), roomTypeMapper.toDTO(entity.getRoomType()));
    }

    public Room toEntity(RoomDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Room(dto.getId(), dto.getName(), dto.getMaxCapacity(), dto.getExtraBedsAvailable(), roomTypeMapper.toEntity(dto.getRoomType()));
    }
}
