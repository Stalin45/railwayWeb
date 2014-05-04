package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name="Trains")
public class Train implements Serializable{

    private Long id;
    private TrainType trainType;
    private Path path;
    private Date date;
    private Integer freeSeats;
    private List<Ticket> ticketList;
    
    public Train() {};
    
    public Train(TrainType trainType, Path path, Date date, Integer freeSeats) {
        this.trainType = trainType;
        this.path = path;
        this.date = date;
        this.freeSeats = freeSeats;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_type")
    public TrainType getTrainType() {
        return trainType;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path")
    public Path getPath() {
        return path;
    }

    @Column(name = "free_seats")
    public Integer getFreeSeats() {
        return freeSeats;
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "train")
    public List<Ticket> getTicketList() {
        return ticketList;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "trainType=" + trainType + ", path=" + path + ", date=" + sdf.format(date);
    }
}