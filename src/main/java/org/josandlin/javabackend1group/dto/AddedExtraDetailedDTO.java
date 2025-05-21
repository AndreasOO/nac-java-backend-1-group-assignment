package org.josandlin.javabackend1group.dto;

public class AddedExtraDetailedDTO {

    Long addedExtraId;
    Long extraTypeId;
    String extraTypeName;
    int cost;

    public AddedExtraDetailedDTO(Long addedExtraId, Long extraTypeId, String extraTypeName, int cost) {
        this.addedExtraId = addedExtraId;
        this.extraTypeId = extraTypeId;
        this.extraTypeName = extraTypeName;
        this.cost = cost;
    }

    public Long getAddedExtraId() {
        return addedExtraId;
    }

    public void setAddedExtraId(Long addedExtraId) {
        this.addedExtraId = addedExtraId;
    }

    public Long getExtraTypeId() {
        return extraTypeId;
    }

    public void setExtraTypeId(Long extraTypeId) {
        this.extraTypeId = extraTypeId;
    }

    public String getExtraTypeName() {
        return extraTypeName;
    }

    public void setExtraTypeName(String extraTypeName) {
        this.extraTypeName = extraTypeName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
