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
@Table(name="Time_table")
public class TimeTable implements Serializable{

    private Long id;
    private Train train;
    private Path path;
    private Date date;
    private Integer freeSeats;
    private List<Ticket> ticketList;
    
    public TimeTable() {};
    
    public TimeTable(Train train, Path path, Date date, Integer freeSeats) {
        this.train = train;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_number")
    public Train getTrain() {
        return train;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "path")
    public Path getPath() {
        return path;
    }

    @Column(name = "free_seats")
    public Integer getFreeSeats() {
        return freeSeats;
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "timetable")
    public List<Ticket> getTicketList() {
        return ticketList;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTrain(Train train) {
        this.train = train;
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
        return "train=" + train + ", path=" + path + ", date=" + sdf.format(date);
    }
}