package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Relation")
public class Relation implements Serializable {
    
    private Long id;
    private Station currentStation;
    private Station nextStation;
    private Float cost;
    
    public Relation() {
    }

    public Relation(Station currentStation, Station nextStation, Float cost) {
        this.currentStation = currentStation;
        this.nextStation = nextStation;
        this.cost = cost;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "current_station")
    public Station getCurrentStation() {
        return currentStation;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "next_station")
    public Station getNextStation() {
        return nextStation;
    }

    @Column(name = "cost")
    public Float getCost() {
        return cost;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.currentStation);
        hash = 13 * hash + Objects.hashCode(this.nextStation);
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
        final Relation other = (Relation) obj;
        if (!Objects.equals(this.currentStation, other.currentStation)) {
            return false;
        }
        if (!Objects.equals(this.nextStation, other.nextStation)) {
            return false;
        }
        return true;
    }
}
