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
    private TimeTable timetable;
    private Long totalCost;
    private Passenger passenger;
//    private User user;

    public Ticket() {
    }

    public Ticket(TimeTable timetable, Long totalCost, Passenger passenger) {
        this.timetable = timetable;
        this.totalCost = totalCost;
        this.passenger = passenger;
//        this.user = user;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timetable_id")
    public TimeTable getTimetable() {
        return timetable;
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

    

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    public User getUser() {
//        return user;
//    }
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public void setTimetable(TimeTable timetable) {
        this.timetable = timetable;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }
}
