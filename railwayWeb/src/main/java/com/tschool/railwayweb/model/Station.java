package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Stations")
public class Station implements Serializable {
    
    private Long id;
    private String name;
    private List<Destination> destination;
    private List<Pathmap> currentStations;
    private List<Pathmap> nextStations;
    
    public Station() {}
 
    public Station(String name) {
        this.name = name;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }
    
    @Column(name = "name")
    public String getName() {
        return name;
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "station")
    public List<Destination> getDestination() {
        return destination;
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "currentStation")
    public List<Pathmap> getCurrentStations() {
        return currentStations;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "nextStation")
    public List<Pathmap> getNextStations() {
        return nextStations;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    public void setName(String name) {
        this.name = name;
    }

    public void setDestination(List<Destination> destination) {
        this.destination = destination;
    }

    public void setCurrentStations(List<Pathmap> currentStations) {
        this.currentStations = currentStations;
    }

    public void setNextStations(List<Pathmap> nextStations) {
        this.nextStations = nextStations;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
