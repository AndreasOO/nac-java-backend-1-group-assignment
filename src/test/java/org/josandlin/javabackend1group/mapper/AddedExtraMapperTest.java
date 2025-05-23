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

//    @Test
//    void testDtoToEntity() {
//        AddedExtraDTO EntityFromDto1 = addedExtraMapper.toDTO(addedExtraEntity1);
//        AddedExtraDTO EntityFromDto2 = addedExtraMapper.toDTO(addedExtraEntity2);
//
//
//        assertEquals(addedExtraEntity1.getId(), EntityFromDto1.getId());
//        assertEquals(1L, addedExtraEntity1.getId());
//        assertEquals(addedExtraEntity1.getExtraType().getId(),EntityFromDto1.getExtraType().getId());
//        assertEquals(2L, EntityFromDto1.getExtraType().getId());
//        assertEquals("bedFromDto",EntityFromDto1.getExtraType().getName());
//
//
//
//        assertEquals(addedExtraEntity2.getId(), EntityFromDto2.getId());
//        assertEquals(3L, addedExtraEntity2.getId());
//        assertEquals(addedExtraEntity2.getExtraType().getId(),EntityFromDto2.getExtraType().getId());
//        assertEquals(2L, EntityFromDto2.getExtraType().getId());
//        assertEquals("bedFromDto",EntityFromDto2.getExtraType().getName());

//    }

}