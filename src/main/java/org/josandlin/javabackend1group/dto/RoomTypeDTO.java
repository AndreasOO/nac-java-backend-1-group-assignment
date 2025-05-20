package org.josandlin.javabackend1group.dto;

public class RoomTypeDTO {

    private Long id;
    private String name;
    private int costPerNight;

    public RoomTypeDTO(Long id, String name, int costPerNight) {
        this.id = id;
        this.name = name;
        this.costPerNight = costPerNight;
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

    public int getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(int costPerNight) {
        this.costPerNight = costPerNight;
    }
}
