package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.dto.PassengerDTO;
import com.tschool.railwayweb.dto.PathDTO;
import com.tschool.railwayweb.dto.TrainDTO;
import com.tschool.railwayweb.dto.TrainTypeDTO;
import com.tschool.railwayweb.model.*;
import com.tschool.railwayweb.util.exception.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainService {
    
    @Autowired
    @Qualifier(value = "railwayDAO")
    private RailwayDAO rDAO = new RailwayDAO();
    
    @Transactional
    public List<TrainDTO> getTrainList() {
        List<TrainDTO> trainListDTO = new ArrayList<TrainDTO>();
        try {
             List<Train> trainList = rDAO.getEntityList(Train.class);
             for (Train train : trainList) {
                trainListDTO.add(new TrainDTO(train.getId(),train.getTrainType().getId(),train.getTrainType().getType(),
                                              train.getPath().getNumber(),train.getDate(),train.getFreeSeats()));
            }
        } catch (FindException ex) {
            Logger.getLogger(TrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trainListDTO;
    }
    
    @Transactional
    public List<TrainDTO> getTrainListUpToDate() throws FindException {
        List<TrainDTO> trainListDTO = new ArrayList<TrainDTO>();
        Calendar actualDate = Calendar.getInstance();
        actualDate.add(Calendar.MINUTE, 10);

        Calendar departureDateTime = Calendar.getInstance();
        List<Train> trainList = rDAO.getEntityList(Train.class);
        for (Train train : trainList) {
            Date departureDate = train.getDate();
            Date departureTime = train.getPath().getDestination().get(0).getTime();
            ////////////////////////////////////////////////////////////////////////////////////
            departureDateTime.setTimeInMillis(departureDate.getTime() + departureTime.getTime() + 3 * 60 * 60 * 1000);
            if (actualDate.compareTo(departureDateTime) < 0) {
                trainListDTO.add(new TrainDTO(train.getId(), train.getTrainType().getId(), train.getTrainType().getType(),
                        train.getPath().getNumber(), train.getDate(), train.getFreeSeats()));
            }
        }
        return trainListDTO;
    }
    
    @Transactional
    public TrainDTO getById(Long privateKey) {
        TrainDTO trainDTO = null;
        try {
            Train train = (Train) rDAO.findByPrimaryKey(Train.class, privateKey);
            trainDTO = new TrainDTO(train.getId(),train.getTrainType().getId(),train.getTrainType().getType(),
                                    train.getPath().getNumber(),train.getDate(),train.getFreeSeats());
        } catch (FindException ex) {
            Logger.getLogger(TrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trainDTO;
    }
    
    @Transactional
    public void addTrain(TrainDTO trainDTO) {
        try {
            Train train = null;
            Integer ticketCount = 0; 
            if (trainDTO.getId() != null) {
                train = (Train) rDAO.findByPrimaryKey(Train.class, trainDTO.getId());
                ticketCount = train.getTicketList().size();
            } else {
                train = new Train();
            }
            TrainType trainType = (TrainType) rDAO.findByPrimaryKey(TrainType.class, trainDTO.getTrainTypeId());
            Path path = (Path) rDAO.findByPrimaryKey(Path.class, trainDTO.getPathId()); 
            Integer freeSeats = trainType.getMaxSeats() - ticketCount;
            train.setTrainType(trainType);
            train.setPath(path);
            train.setDate(trainDTO.getDate());
            train.setFreeSeats(freeSeats);
            rDAO.addEntity(train);
        } catch (CreateException ex) {
            Logger.getLogger(TrainService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindException ex) {
            Logger.getLogger(TrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Transactional
    public void delete(Long trainId) {
        try {
            Train train = (Train) rDAO.findByPrimaryKey(Train.class, trainId);
            rDAO.remove(train);
        } catch (RemoveException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Transactional
    public List<TrainDTO> getTrainsFitStations(String stationFrom, String stationTo) throws FindException {
        Calendar actualDate = Calendar.getInstance();
        actualDate.add(Calendar.MINUTE, 10);

        Calendar departureDateTime = Calendar.getInstance();
        //1. Find up to date trains
        List<Train> trainList = rDAO.getEntityList(Train.class);
        for (Iterator iterator = trainList.iterator(); iterator.hasNext();) {
            Train train = (Train) iterator.next();
            Date departureDate = train.getDate();
            Date departureTime = train.getPath().getDestination().get(0).getTime();
            departureDateTime.setTimeInMillis(departureDate.getTime() + departureTime.getTime() + 3 * 60 * 60 * 1000);
            if (actualDate.compareTo(departureDateTime) > 0) {
                iterator.remove();
            }
        }
        List<TrainDTO> trainDTOList = new ArrayList<TrainDTO>();
        //2.Check if trains fit stations
        if (stationTo.isEmpty()) {
            for (int i=0; i<trainList.size(); i++) {
                Train train = trainList.get(i);
                List<Destination> destinationList = train.getPath().getDestination();
                for (Destination destination : destinationList) {
                    if (stationFrom.equals(destination.getStation().getName())) {
                        trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
                        
                        break;
                    }
                }
            }
        } else if (stationFrom.isEmpty()) {
            for (Train train : trainList) {
                List<Destination> destinationList = train.getPath().getDestination();
                for (Destination destination : destinationList) {
                    if (stationTo.equals(destination.getStation().getName())) {
                        trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
                        break;
                    }
                }
            }
        } else {
            for (Train train : trainList) {
                Boolean isFromStationFound = false;
                List<Destination> destinationList = train.getPath().getDestination();
                for (Destination destination : destinationList) {
                    if (stationFrom.equals(destination.getStation().getName())) {
                        isFromStationFound = true;
                        continue;
                    }
                    if (isFromStationFound && stationTo.equals(destination.getStation().getName())) {
                        trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
                        break;
                    }
                }
            }
        }
        if (trainDTOList.size() == 0) {
            throw new FindException("No trains found");
        }
        return trainDTOList;
    }
    
    @Transactional
    public List<TrainDTO> getTrainsFitDate(Date date) throws FindException {
        Calendar actualDate = Calendar.getInstance();
        actualDate.add(Calendar.MINUTE, 10);

        Calendar departureDateTime = Calendar.getInstance();
        //1. Find up to date trains
        List<Train> trainList = rDAO.getEntityList(Train.class);
        for (Iterator iterator = trainList.iterator(); iterator.hasNext();) {
            Train train = (Train) iterator.next();
            Date departureDate = train.getDate();
            Date departureTime = train.getPath().getDestination().get(0).getTime();
            departureDateTime.setTimeInMillis(departureDate.getTime() + departureTime.getTime() + 3 * 60 * 60 * 1000);
            if (actualDate.compareTo(departureDateTime) > 0) {
                iterator.remove();
            }
        }
        List<TrainDTO> trainDTOList = new ArrayList<TrainDTO>();
        //2.Check if trains fit date
        trainList = rDAO.whichTrainsFitDate(date, trainList);
        for(Train train : trainList) {
            trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
        }
        return trainDTOList;
    }
    
    @Transactional
    public List<TrainDTO> getTrainsFitStationsDate(Date date, String stationFrom, String stationTo) throws FindException {
        Calendar actualDate = Calendar.getInstance();
        actualDate.add(Calendar.MINUTE, 10);

        Calendar departureDateTime = Calendar.getInstance();
        //1. Find up to date trains
        List<Train> trainList = rDAO.getEntityList(Train.class);
        for (int i=0; i<trainList.size(); i++) {
            Date departureDate = trainList.get(i).getDate();
            Date departureTime = trainList.get(i).getPath().getDestination().get(0).getTime();
            departureDateTime.setTimeInMillis(departureDate.getTime() + departureTime.getTime() + 3 * 60 * 60 * 1000);
            if (actualDate.compareTo(departureDateTime) > 0) {
                trainList.remove(i);
            }
        }
        List<TrainDTO> trainDTOList = new ArrayList<TrainDTO>();
        //2.Check if trains fit date
        trainList = rDAO.whichTrainsFitDate(date, trainList);
        //3.Check if trains fit stations
        if (stationTo.isEmpty()) {
            for (int i=0; i<trainList.size(); i++) {
                Train train = trainList.get(i);
                List<Destination> destinationList = train.getPath().getDestination();
                for (Destination destination : destinationList) {
                    if (stationFrom.equals(destination.getStation().getName())) {
                        trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
                        
                        break;
                    }
                }
            }
        } else if (stationFrom.isEmpty()) {
            for (Train train : trainList) {
                List<Destination> destinationList = train.getPath().getDestination();
                for (Destination destination : destinationList) {
                    if (stationTo.equals(destination.getStation().getName())) {
                        trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
                        break;
                    }
                }
            }
        } else {
            for (Train train : trainList) {
                Boolean isFromStationFound = false;
                List<Destination> destinationList = train.getPath().getDestination();
                for (Destination destination : destinationList) {
                    if (stationFrom.equals(destination.getStation().getName())) {
                        isFromStationFound = true;
                        continue;
                    }
                    if (isFromStationFound && stationTo.equals(destination.getStation().getName())) {
                        trainDTOList.add(new TrainDTO(train.getId(),train.getTrainType().getId(),
                                                      train.getTrainType().getType(),train.getPath().getNumber(),
                                                      train.getDate(),train.getFreeSeats()));
                        break;
                    }
                }
            }
        }
        if (trainDTOList.size() == 0) {
            throw new FindException("No trains found");
        }
        return trainDTOList;
    }
 }