package com.tschool.railwayweb.dto;

import java.util.Date;

public class TrainDTO {
    
    private Long id;
    private Long trainTypeId;
    private String trainTypeType;
    private Long pathId;
    private Date date;
    private Integer freeSeats;

    public TrainDTO() {
    }
    
    public TrainDTO(Long id, Long trainTypeId, String trainTypeType, Long pathId, Date date) {
        this.id = id;
        this.trainTypeId = trainTypeId;
        this.trainTypeType = trainTypeType;
        this.pathId = pathId;
        this.date = date;
    }

    public TrainDTO(Long id, Long trainTypeId, String trainTypeType, Long pathId, Date date, Integer freeSeats) {
        this.id = id;
        this.trainTypeId = trainTypeId;
        this.trainTypeType = trainTypeType;
        this.pathId = pathId;
        this.date = date;
        this.freeSeats = freeSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(Long trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getTrainTypeType() {
        return trainTypeType;
    }

    public void setTrainTypeType(String trainTypeType) {
        this.trainTypeType = trainTypeType;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }
}
