package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@Entity
public class ExtraType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int cost;

    @OneToMany
    @JoinColumn(name = "extras_id")
    private List<Extra> extras;

    public ExtraType(Long id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

}
