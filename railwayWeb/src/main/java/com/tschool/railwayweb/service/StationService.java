package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.dto.RelationDTO;
import com.tschool.railwayweb.dto.StationDTO;
import com.tschool.railwayweb.model.*;
import com.tschool.railwayweb.util.exception.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StationService {
    
    @Autowired
    @Qualifier(value = "railwayDAO")
    private RailwayDAO rDAO = new RailwayDAO();
    
    @Transactional
    public List<StationDTO> getStationList() {
        List<StationDTO> stationListDTO = new ArrayList<StationDTO>();
        try {
             List<Station> stationList = rDAO.getEntityList(Station.class);
             for (Station station : stationList) {
                 stationListDTO.add(new StationDTO(station.getId(), station.getName()));
             }
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stationListDTO;
    }
    
    @Transactional
    public List<String> getStationNameList() {
        List<String> stationNameList = new ArrayList<String>();
        try {
            List<Station> stationList = rDAO.getEntityList(Station.class);
            for (Station station : stationList) {
                stationNameList.add(station.getName());
            }
        } catch (FindException ex) {
        }
        return stationNameList;
    }

    @Transactional
    public StationDTO getById(Long privateKey) {
        StationDTO stationDTO = null;
        try {
            Station station = (Station) rDAO.findByPrimaryKey(Station.class, privateKey);
            stationDTO = new StationDTO(station.getId(), station.getName());
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stationDTO;
    }

    @Transactional
    public void createStation(StationDTO stationDTO, String newStationName, List<RelationDTO> relationListDTO) throws TrainHasTickets, RemoveException, DataStoreException {
        try {
            List<Relation> currentStationList = new ArrayList<Relation>();
            List<Relation> nextStationList = new ArrayList<Relation>();
            Station station = null;
            String oldStationName = stationDTO.getName();
            if (stationDTO.getId() != null) {
                station = (Station) rDAO.findByPrimaryKey(Station.class, stationDTO.getId());
                station.setName(newStationName);
            } else {
                station = new Station();
                station.setName(newStationName);
            }
            for (RelationDTO relationDTO : relationListDTO) {
                if (oldStationName.equals(relationDTO.getCurrentStationName())) {
                    Station relatedStation = new Station(relationDTO.getNextStationId(), relationDTO.getNextStationName());
                    Relation relation = new Relation(station, relatedStation, relationDTO.getCost());
                    currentStationList.add(relation);
                } else {
                    Station relatedStation = new Station(relationDTO.getCurrentStationId(), relationDTO.getCurrentStationName());
                    Relation relation = new Relation(relatedStation, station, relationDTO.getCost());
                    nextStationList.add(relation);
                }
            }

            ///////////////////////////
            List<Destination> destinationList = station.getDestination();
            if (destinationList != null) {
                for (Destination destination : destinationList) {
                    List<Train> trainList = destination.getPath().getTrain();
                    if (trainList != null) {
                        for (Train train : trainList) {
                            if (!train.getTicketList().isEmpty()) {
                                throw new TrainHasTickets("Train with this station has tickets");
                            }
                        }
                    }
                }
            }
            if (station.getCurrentStations() != null) {
                for (int i = 0; i < station.getCurrentStations().size(); i++) {
                    Relation relation = station.getCurrentStations().get(i);
                    rDAO.remove(relation);
                }
            }
            if (station.getNextStations() != null) {
                for (int i = 0; i < station.getNextStations().size(); i++) {
                    Relation relation = station.getNextStations().get(i);
                    rDAO.remove(relation);
                }
            }
            if (station.getCurrentStations() != null)
                station.getCurrentStations().clear();
            if (station.getNextStations() != null) 
                station.getNextStations().clear();
            station.setCurrentStations(currentStationList);
            station.setNextStations(nextStationList);
            ////////////////////////
            rDAO.addEntity(station);
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Transactional
    public List<RelationDTO> getRelationList(StationDTO stationDTO) {
        List<RelationDTO> relationListDTO = new ArrayList<RelationDTO>();
        try {
            List<Relation> relationList = rDAO.getRelationList(stationDTO.getId());
            for (Relation relation : relationList) {
                relationListDTO.add(new RelationDTO(relation.getCurrentStation().getId(),
                        relation.getCurrentStation().getName(),
                        relation.getNextStation().getId(),
                        relation.getNextStation().getName(),
                        relation.getCost()));
            }
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relationListDTO;
    }

    @Transactional
    public List<StationDTO> getRelatedStations(StationDTO stationDTO) {
        List<StationDTO> relatedStationListDTO = new ArrayList<StationDTO>();
        try {
            Station station = (Station) rDAO.findByPrimaryKey(Station.class, stationDTO.getId());
            List<Relation> relationList = station.getCurrentStations();
            for (Relation relation: relationList) {
                relatedStationListDTO.add(new StationDTO(relation.getNextStation().getId(),relation.getNextStation().getName()));
            }
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relatedStationListDTO;
    }
    
    @Transactional
    public void delete(Long stationId) throws TrainHasTickets, RemoveException, FindException {
        Station station = (Station) rDAO.findByPrimaryKey(Station.class, stationId);
        List<Destination> destinationList = station.getDestination();
        if (destinationList != null)
        for (Destination destination : destinationList) {
            List<Train> trainList = destination.getPath().getTrain();
            if (trainList != null)
            for (Train train : trainList) {
                if (!train.getTicketList().isEmpty()) {
                    throw new TrainHasTickets("Train with this station has tickets");
                }
            }
        }
        rDAO.remove(station);
    }
}