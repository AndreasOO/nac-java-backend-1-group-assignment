package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.RoomTypeDTO;
import org.josandlin.javabackend1group.entity.RoomType;
import org.springframework.stereotype.Component;

@Component
public class RoomTypeMapper {

    public RoomTypeDTO toDTO(RoomType entity) {
        if (entity == null) {
            return null;
        }
        return new RoomTypeDTO(entity.getId(), entity.getName(), entity.getCostPerNight());
    }

    public RoomType toEntity(RoomTypeDTO dto) {
        if (dto == null) {
            return null;
        }
        return new RoomType(dto.getId(), dto.getName(), dto.getCostPerNight());
    }
}
