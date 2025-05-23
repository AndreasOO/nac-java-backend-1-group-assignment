package org.josandlin.javabackend1group.mapper;

import org.josandlin.javabackend1group.dto.AddedExtraDTO;
import org.josandlin.javabackend1group.dto.ExtraTypeDTO;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.josandlin.javabackend1group.entity.ExtraType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddedExtraMapperTest {

    AddedExtraMapper addedExtraMapper = new AddedExtraMapper(new ExtraTypeMapper());

    ExtraType extraBedEntity = new ExtraType(2L,"bedFromEntity",200);
    AddedExtra addedExtraEntity1 = new AddedExtra(1L, extraBedEntity);
    AddedExtra addedExtraEntity2 = new AddedExtra(3L, extraBedEntity);


    ExtraTypeDTO extraBedDto = new ExtraTypeDTO(4L,"bedFromDto",200);
    AddedExtraDTO addedExtraDto1 = new AddedExtraDTO(5L,extraBedDto);
    AddedExtraDTO addedExtraDto2 = new AddedExtraDTO(6L,extraBedDto);


    @Test
    void testEntityToDto() {
        AddedExtraDTO dtoFromEntity1 = addedExtraMapper.toDTO(addedExtraEntity1);
        AddedExtraDTO dtoFromEntity2 = addedExtraMapper.toDTO(addedExtraEntity2);


        assertEquals(addedExtraEntity1.getId(), dtoFromEntity1.getId());
        assertEquals(1L, addedExtraEntity1.getId());
        assertEquals(addedExtraEntity1.getExtraType().getId(),dtoFromEntity1.getExtraType().getId());
        assertEquals(2L, dtoFromEntity1.getExtraType().getId());
        assertEquals("bedFromEntity",dtoFromEntity1.getExtraType().getName());



        assertEquals(addedExtraEntity2.getId(), dtoFromEntity2.getId());
        assertEquals(3L, addedExtraEntity2.getId());
        assertEquals(addedExtraEntity2.getExtraType().getId(),dtoFromEntity2.getExtraType().getId());
        assertEquals(2L, dtoFromEntity2.getExtraType().getId());
        assertEquals("bedFromEntity",dtoFromEntity2.getExtraType().getName());
    }

    @Test
    void testDtoToEntity() {
        AddedExtra entityFromDto1 = addedExtraMapper.toEntity(addedExtraDto1);
        AddedExtra entityFromDto2 = addedExtraMapper.toEntity(addedExtraDto2);


        assertEquals(addedExtraDto1.getId(), entityFromDto1.getId());
        assertEquals(5L, addedExtraDto1.getId());
        assertEquals(addedExtraDto1.getExtraType().getId(),entityFromDto1.getExtraType().getId());
        assertEquals(4L, entityFromDto1.getExtraType().getId());
        assertEquals("bedFromDto",entityFromDto1.getExtraType().getName());



        assertEquals(addedExtraDto2.getId(), entityFromDto2.getId());
        assertEquals(6L, addedExtraDto2.getId());
        assertEquals(addedExtraDto2.getExtraType().getId(),entityFromDto2.getExtraType().getId());
        assertEquals(4L, entityFromDto2.getExtraType().getId());
        assertEquals("bedFromDto",entityFromDto2.getExtraType().getName());

    }

}