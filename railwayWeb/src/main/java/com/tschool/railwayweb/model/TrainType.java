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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Train_types")
public class TrainType implements Serializable {
    
    private Long id;
    private String type = "";
    private Float costMultiplier = 1.0F;
    private Integer maxSeats = 100;
    private List<Train> trainList;

    public TrainType() {}

    public TrainType(String type, Float costMultiplier, Integer maxSeats) {
        this.type = type;
        this.costMultiplier = costMultiplier;
        this.maxSeats = maxSeats;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    @Column(name = "cost_multiplier")
    public Float getCostMultiplier() {
        return costMultiplier;
    }

    @Column(name = "max_seats")
    public Integer getMaxSeats() {
        return maxSeats;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "trainType")
    public List<Train> getTrainList() {
        return trainList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCostMultiplier(Float costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public void setTrainList(List<Train> trainList) {
        this.trainList = trainList;
    }

    @Override
    public String toString() {
        return type;
    }

}
