package com.tschool.railwayweb.dto;

public class PassengerDTO {
    
    private Long ticketId;
    private Long totalCost;
    private String name;
    private String secondName;
    private Long passport;

    public PassengerDTO() {
    }

    public PassengerDTO(Long ticketId, Long totalCost, String name, String secondName, Long passport) {
        this.ticketId = ticketId;
        this.totalCost = totalCost;
        this.name = name;
        this.secondName = secondName;
        this.passport = passport;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getPassport() {
        return passport;
    }

    public void setPassport(Long passport) {
        this.passport = passport;
    }
}
