package com.tschool.railwayweb.dto;

import java.util.List;

public class PathFormDTO {
    
    private PathDTO path;
    private List<StationDTO> nextStationList;
    private List<DestinationDTO> destinationList;

    public PathFormDTO() {
    }

    public PathFormDTO(PathDTO path, List<StationDTO> nextStationList, List<DestinationDTO> destinationList) {
        this.path = path;
        this.nextStationList = nextStationList;
        this.destinationList = destinationList;
    }

    public PathDTO getPath() {
        return path;
    }

    public void setPath(PathDTO path) {
        this.path = path;
    }

    public List<StationDTO> getNextStationList() {
        return nextStationList;
    }

    public void setNextStationList(List<StationDTO> nextStationList) {
        this.nextStationList = nextStationList;
    }

    public List<DestinationDTO> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(List<DestinationDTO> destinationList) {
        this.destinationList = destinationList;
    }
}
