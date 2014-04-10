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
@Table(name = "Paths")
public class Path implements Serializable {
    
    private Long number;
    private List<Destination> destination;
    private List<TimeTable> timeTable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getNumber() {
        return number;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "path")
    public List<Destination> getDestination() {
        return destination;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "path")
    public List<TimeTable> getTimeTable() {
        return timeTable;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setDestination(List<Destination> destination) {
        this.destination = destination;
    }

    public void setTimeTable(List<TimeTable> timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
