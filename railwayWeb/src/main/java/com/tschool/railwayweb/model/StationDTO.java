package com.tschool.railwayweb.model;

import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Station;
import java.io.Serializable;
import java.util.List;

public class StationDTO implements Serializable{
    
    private Station station;
    private List<Pathmap> pathmapList;

    public StationDTO() {
    }

    public StationDTO(Station station) {
        this.station = station;
    }
    
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Pathmap> getPathmapList() {
        return pathmapList;
    }

    public void setPathmapList(List<Pathmap> pathmapList) {
        this.pathmapList = pathmapList;
    }
}
