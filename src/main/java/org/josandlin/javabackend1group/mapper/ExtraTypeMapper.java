package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.ExtraTypeDTO;
import org.josandlin.javabackend1group.entity.ExtraType;
import org.springframework.stereotype.Component;

@Component
public class ExtraTypeMapper {

    public ExtraTypeDTO toDTO(ExtraType entity) {
        if (entity == null) {
            return null;
        }
        return new ExtraTypeDTO(entity.getId(), entity.getName(), entity.getCost());
    }

    public ExtraType toEntity(ExtraTypeDTO dto) {
        if (dto == null) {
            return null;
        }
        return new ExtraType(dto.getId(), dto.getName(), dto.getCost());
    }
}

