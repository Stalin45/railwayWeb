package com.tschool.railwayweb.dto;

import java.util.Date;

public class DestinationDTO {
    private Integer number;
    private Long pathId;
    private Long stationId;
    private String stationName;
    private Date time;

    public DestinationDTO() {
    }

    public DestinationDTO(Integer number, Long pathId, Long stationId, String stationName, Date time) {
        this.number = number;
        this.pathId = pathId;
        this.stationId = stationId;
        this.stationName = stationName;
        this.time = new Date(time.getTime());
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }
}
