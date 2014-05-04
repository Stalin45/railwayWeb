package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.dto.DestinationDTO;
import com.tschool.railwayweb.dto.PathDTO;
import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Relation;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.model.Train;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.RemoveException;
import com.tschool.railwayweb.util.exception.TrainHasTicketsException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PathService {
    
    @Autowired
    @Qualifier(value = "railwayDAO")
    RailwayDAO rDAO = new RailwayDAO();
    
    @Transactional
    public List<PathDTO> getPathList() {
        List<PathDTO> pathListDTO = new ArrayList<PathDTO>();
        try {
             List<Path> pathList = rDAO.getEntityList(Path.class);
             for (Path path : pathList) {
                 pathListDTO.add(new PathDTO(path.getNumber()));
             }
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pathListDTO;
    }
    
    @Transactional
    public PathDTO getById(Long privateKey) {
        PathDTO pathDTO = null;
        try {
            Path path = (Path) rDAO.findByPrimaryKey(Path.class, privateKey);
            pathDTO = new PathDTO(path.getNumber());
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pathDTO;
    }

    @Transactional
    public void createPath(PathDTO pathDTO, List<DestinationDTO> destinationListDTO) throws TrainHasTicketsException, FindException, RemoveException, CreateException {
        Path path = null;
        if (pathDTO.getId() != null) {
            path = (Path) rDAO.findByPrimaryKey(Path.class, pathDTO.getId());
        } else {
            path = new Path();
        }
        List<Destination> newDestinationList = new ArrayList<Destination>();
        for (DestinationDTO destinationDTO : destinationListDTO) {
            Station station = new Station(destinationDTO.getStationId(), destinationDTO.getStationName());
            newDestinationList.add(new Destination(destinationDTO.getNumber(), station, destinationDTO.getTime(), path));
        }
        List<Train> trainList = path.getTrain();
        if (trainList != null) {
            for (Train train : trainList) {
                if (!train.getTicketList().isEmpty()) {
                    throw new TrainHasTicketsException("Train with this station has tickets");
                }
            }
        }
        if (path.getDestination() != null) {
            for (int i = 0; i < path.getDestination().size(); i++) {
                Destination destination = path.getDestination().get(i);
                rDAO.remove(destination);
            }
        }
        if (path.getDestination() != null) {
            path.getDestination().clear();
        }
        path.setDestination(newDestinationList);
        rDAO.addEntity(path);
    }
    
    @Transactional
    public List<DestinationDTO> getDestinationList(PathDTO pathDTO) {
        List<DestinationDTO> destinationListDTO = new ArrayList<DestinationDTO>();
        try {
             List<Destination> destinationList = rDAO.getDestinationList(pathDTO.getId());
             for (Destination destination : destinationList) {
                 destinationListDTO.add(new DestinationDTO(destination.getNumber(),
                                                           destination.getPath().getNumber(),
                                                           destination.getStation().getId(),
                                                           destination.getStation().getName(),
                                                           destination.getTime()));
             }
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return destinationListDTO;
    }
    
    @Transactional
    public void delete(Long pathNumber) throws FindException, TrainHasTicketsException, RemoveException {
        Path path = (Path) rDAO.findByPrimaryKey(Path.class, pathNumber);
        List<Train> trainList = path.getTrain();
        if (trainList != null) {
            for (Train train : trainList) {
                if (!train.getTicketList().isEmpty()) {
                    throw new TrainHasTicketsException("Train with this station has tickets");
                }
            }
        }
        rDAO.remove(path);
    }
}
