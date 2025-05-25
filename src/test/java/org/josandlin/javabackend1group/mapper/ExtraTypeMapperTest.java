package org.josandlin.javabackend1group.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.josandlin.javabackend1group.dto.CustomerDTO;
import org.josandlin.javabackend1group.dto.ExtraTypeDTO;
import org.josandlin.javabackend1group.entity.Customer;
import org.josandlin.javabackend1group.entity.ExtraType;
import org.junit.jupiter.api.Test;

class ExtraTypeMapperTest {
    ExtraTypeMapper extraTypeMapper = new ExtraTypeMapper();

    ExtraType extraTypeEntity = new ExtraType(1L, "bedEntity", 200);
    ExtraTypeDTO extraTypeDTO = new ExtraTypeDTO(2L, "bedDto", 200);



    @Test
    void testEntityToDto() {

        ExtraTypeDTO extraTypeDTOFromEntity = extraTypeMapper.toDTO(extraTypeEntity);




        assertEquals(1L, extraTypeDTOFromEntity.getId());
        assertEquals(extraTypeEntity.getId(), extraTypeDTOFromEntity.getId());


        assertEquals("bedEntity", extraTypeDTOFromEntity.getName());
        assertEquals(extraTypeEntity.getName(), extraTypeDTOFromEntity.getName());


    }

    @Test
    void testDtoToEntity() {

        ExtraType extraTypeEntityFromDto = extraTypeMapper.toEntity(extraTypeDTO);


        assertEquals(2L, extraTypeEntityFromDto.getId());
        assertEquals(extraTypeDTO.getId(), extraTypeEntityFromDto.getId());


        assertEquals("bedDto", extraTypeEntityFromDto.getName());
        assertEquals(extraTypeDTO.getName(), extraTypeEntityFromDto.getName());


    }

}