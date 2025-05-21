package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.AddedExtraDTO;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddedExtraMapper {


    private final ExtraTypeMapper extraTypeMapper;

    @Autowired
    public AddedExtraMapper(ExtraTypeMapper extraTypeMapper) {
        this.extraTypeMapper = extraTypeMapper;
    }

    public AddedExtraDTO toDTO(AddedExtra entity) {
        if (entity == null) {
            return null;
        }
        return new AddedExtraDTO(entity.getId(), extraTypeMapper.toDTO(entity.getExtraType()));
    }

    public AddedExtra toEntity(AddedExtraDTO dto) {
        if (dto == null) {
            return null;
        }
        return new AddedExtra(dto.getId(), extraTypeMapper.toEntity(dto.getExtraType()));
    }

}
