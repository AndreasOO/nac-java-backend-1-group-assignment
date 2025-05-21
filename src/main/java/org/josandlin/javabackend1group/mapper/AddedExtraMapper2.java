package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dao.ExtraTypeDao;
import org.josandlin.javabackend1group.dto.AddedExtraDetailedDTO;
import org.josandlin.javabackend1group.dto.RoomDetailedDTO;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddedExtraMapper2 {

    ExtraTypeDao extraTypeDao;

    @Autowired
    public AddedExtraMapper2(ExtraTypeDao extraTypeDao) {
        this.extraTypeDao = extraTypeDao;
    }

    public AddedExtraDetailedDTO toDTO(AddedExtra entity) {
        if (entity == null) {
            return null;
        }
        return new AddedExtraDetailedDTO(entity.getId(),
                                        entity.getExtraType().getId(),
                                        entity.getExtraType().getName(),
                                        entity.getExtraType().getCost());
    }

    public AddedExtra toEntity(AddedExtraDetailedDTO dto) {
        if (dto == null) {
            return null;
        }
        return new AddedExtra(dto.getAddedExtraId(),
                                extraTypeDao.findById(dto.getExtraTypeId()).orElseThrow(() -> new IllegalArgumentException("Extra type not found")));
    }
}
