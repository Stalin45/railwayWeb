package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Destinations")
public class Destination implements Serializable {
    
    private Long id;
    private Station station;
    private Date time;
    private Path path;
    private Integer number;
            
    public Destination() {}

    public Destination(Integer number, Station station, Date time, Path path) {
        this.number = number;
        this.station = station;
        this.time = time;
        this.path = path;
        this.number = number;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="station_id")
    public Station getStation() {
        return station;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="path_number")
    public Path getPath() {
        return path;
    }
    
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setPath(Path path) {
        this.path = path;
    }  

    public void setNumber(Integer number) {
        this.number = number;
    }
}
