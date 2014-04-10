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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Trains")
public class Train implements Serializable {
    
    private Long number;
    private TrainType trainType;
    private Integer maxSeats;
    private List<TimeTable> timetable;
    
    public Train() {};

    public Train(TrainType trainType, Integer maxSeats) {
        this.trainType = trainType;
        this.maxSeats = maxSeats;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    public Long getNumber() {
        return number;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    public TrainType getTrainType() {
        return trainType;
    }

    @Column(name = "max_seat")
    public Integer getMaxSeats() {
        return maxSeats;
    }
  
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "train")
    public List<TimeTable> getTimetable() {
        return timetable;
    }
    
    public void setTimetable(List<TimeTable> timetable) {
        this.timetable = timetable;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Override
    public String toString() {
        return "№ " + number + " " + trainType;
    }
    
}
