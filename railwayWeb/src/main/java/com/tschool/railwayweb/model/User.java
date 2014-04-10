package com.tschool.railwayweb.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("U")
public class User extends SuperUser implements Serializable {
    
//    private List<Ticket> ticketList; 
//    
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
//    public List<Ticket> getTicketList() {
//        return ticketList;
//    }
//
//    public void setTicketList(List<Ticket> ticketList) {
//        this.ticketList = ticketList;
//    }
            
}
