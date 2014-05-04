package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="Tickets")
public class Ticket implements Serializable {
    
    private Long id;
    private Train train;
    private Long totalCost;
    private Passenger passenger;
    
    public Ticket() {
    }

    public Ticket(Train train, Long totalCost, Passenger passenger) {
        this.train = train;
        this.totalCost = totalCost;
        this.passenger = passenger;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    public Train getTrain() {
        return train;
    }

    @Column(name = "total_cost")
    public Long getTotalCost() {
        return totalCost;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    public Passenger getPassenger() {
        return passenger;
    }  
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }
}
