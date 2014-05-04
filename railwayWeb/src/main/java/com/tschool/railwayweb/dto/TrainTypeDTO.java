package com.tschool.railwayweb.dto;

public class TrainTypeDTO {
    
    private Long id;
    private String type;
    private Float costMultiplier;
    private Integer maxSeats;

    public TrainTypeDTO() {
    }

    public TrainTypeDTO(Long id, String type, Float costMultiplier, Integer maxSeats) {
        this.id = id;
        this.type = type;
        this.costMultiplier = costMultiplier;
        this.maxSeats = maxSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCostMultiplier() {
        return costMultiplier;
    }

    public void setCostMultiplier(Float costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }
    
}
