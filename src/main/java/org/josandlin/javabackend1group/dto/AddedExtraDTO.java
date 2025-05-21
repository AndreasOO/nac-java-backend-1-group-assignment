package org.josandlin.javabackend1group.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.josandlin.javabackend1group.entity.ExtraType;

public class AddedExtraDTO {

    private Long id;
    private ExtraTypeDTO extraType;

    public AddedExtraDTO(Long id, ExtraTypeDTO extraType) {
        this.id = id;
        this.extraType = extraType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtraTypeDTO getExtraType() {
        return extraType;
    }

    public void setExtraType(ExtraTypeDTO extraType) {
        this.extraType = extraType;
    }
}
