package org.josandlin.javabackend1group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AddedExtra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private ExtraType extraType;

    public AddedExtra(ExtraType extraType) {
        this.extraType = extraType;
    }


}
