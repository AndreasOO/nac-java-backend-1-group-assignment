package org.josandlin.javabackend1group.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Customer name-field cannot be empty")
    @Size(min = 2, message = "Customer name-field must contain at least two character")
    private String name;

    public CustomerDTO() {}

    public CustomerDTO(String name) {
        this.name = name;
    }

    public CustomerDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
