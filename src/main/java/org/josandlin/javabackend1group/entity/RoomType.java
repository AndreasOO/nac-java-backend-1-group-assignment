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
public class RoomType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "rooms_id")
    private List<Room> rooms;

    public RoomType(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
