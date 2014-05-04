package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
    private List<Relation> currentStations;
    private List<Relation> nextStations;
    
    public Station() {}
 
    public Station(Long id, String name) {
        this.id = id;
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
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "currentStation")
    public List<Relation> getCurrentStations() {
        return currentStations;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "nextStation")
    public List<Relation> getNextStations() {
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

    public void setCurrentStations(List<Relation> currentStations) {
        this.currentStations = currentStations;
    }

    public void setNextStations(List<Relation> nextStations) {
        this.nextStations = nextStations;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Station other = (Station) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
