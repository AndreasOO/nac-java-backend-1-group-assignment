package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExtraType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private int cost;

    @OneToMany
    @JoinColumn(name = "extra_id")
    private List<Extra> extras;

}
