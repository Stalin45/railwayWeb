package com.tschool.railwayweb.dto;

public class TicketDTO {
    
    private Long trainId;
    private String name;
    private String secondName;
    private Long passport;

    public TicketDTO() {
    }

    public TicketDTO(Long trainId, String name, String secondName, Long passport) {
        this.trainId = trainId;
        this.name = name;
        this.secondName = secondName;
        this.passport = passport;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
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
