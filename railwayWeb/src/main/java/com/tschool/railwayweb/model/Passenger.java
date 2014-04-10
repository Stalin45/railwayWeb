package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "passengers")
public class Passenger implements Serializable {
    
    private Long passport;
    private String name;
    private String secondName;
    private List<Ticket> ticketList;

    public Passenger() {
    }

    public Passenger(Long passport, String name, String secondName) {
        this.passport = passport;
        this.name = name;
        this.secondName = secondName;
    }

    @Id
    @Column(name = "passport")
    public Long getPassport() {
        return passport;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "second_name")
    public String getSecondName() {
        return secondName;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "passenger")
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setPassport(Long passport) {
        this.passport = passport;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
    
}
