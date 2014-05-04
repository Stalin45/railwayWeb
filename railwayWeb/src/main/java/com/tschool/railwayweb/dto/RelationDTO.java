package com.tschool.railwayweb.dto;

import java.util.Objects;

public class RelationDTO {
    
    Long currentStationId;
    String currentStationName;
    Long nextStationId;
    String nextStationName;
    Float cost;

    public RelationDTO() {}
    
    public RelationDTO(Long currentStationId, String currentStationName, Long nextStationId, String nextStationName, Float cost) {
        this.currentStationId = currentStationId;
        this.currentStationName = currentStationName;
        this.nextStationId = nextStationId;
        this.nextStationName = nextStationName;
        this.cost = cost;
    }

    public Long getCurrentStationId() {
        return currentStationId;
    }

    public void setCurrentStationId(Long currentStationId) {
        this.currentStationId = currentStationId;
    }

    public String getCurrentStationName() {
        return currentStationName;
    }

    public void setCurrentStationName(String currentStationName) {
        this.currentStationName = currentStationName;
    }

    public Long getNextStationId() {
        return nextStationId;
    }

    public void setNextStationId(Long nextStationId) {
        this.nextStationId = nextStationId;
    }

    public String getNextStationName() {
        return nextStationName;
    }

    public void setNextStationName(String nextStationName) {
        this.nextStationName = nextStationName;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.currentStationId);
        hash = 79 * hash + Objects.hashCode(this.nextStationId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RelationDTO other = (RelationDTO) obj;
        if (!Objects.equals(this.currentStationId, other.currentStationId)) {
            return false;
        }
        if (!Objects.equals(this.nextStationId, other.nextStationId)) {
            return false;
        }
        return true;
    }
    
}
