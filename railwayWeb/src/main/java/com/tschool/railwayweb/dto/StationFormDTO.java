package com.tschool.railwayweb.dto;

import java.util.List;

public class StationFormDTO {
    
    private StationDTO station;
    private String newStationName;
    private List<StationDTO> nextStationList; 
    private List<RelationDTO> relationList;

    public StationFormDTO() {
    }

    public StationFormDTO(List<StationDTO> nextStationList, List<RelationDTO> relationList) {
        this.nextStationList = nextStationList;
        this.relationList = relationList;
    }
    
    public StationFormDTO(StationDTO station, List<StationDTO> nextStationList, List<RelationDTO> relationList) {
        this.station = station;
        this.nextStationList = nextStationList;
        this.relationList = relationList;
    }
    
    public List<StationDTO> getNextStationList() {
        return nextStationList;
    }

    public void setNextStationList(List<StationDTO> nextStationList) {
        this.nextStationList = nextStationList;
    }

    public List<RelationDTO> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<RelationDTO> relationList) {
        this.relationList = relationList;
    }

    public StationDTO getStation() {
        return station;
    }

    public void setStation(StationDTO station) {
        this.station = station;
    }

    public String getNewStationName() {
        return newStationName;
    }

    public void setNewStationName(String newStationName) {
        this.newStationName = newStationName;
    }
    
}
