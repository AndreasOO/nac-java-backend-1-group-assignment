package org.josandlin.javabackend1group.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.josandlin.javabackend1group.entity.ExtraType;

public class AddedExtraDTO extends AbstractDTO {

    private Long id;

    @Valid
    @NotNull
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
