package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.dto.DestinationDTO;
import com.tschool.railwayweb.dto.PathDTO;
import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Relation;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.RemoveException;
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
    public void createPath(PathDTO pathDTO, List<DestinationDTO> destinationListDTO) {
        try {
            Path path = null;
            if (pathDTO.getId() != null) {
                path = (Path) rDAO.findByPrimaryKey(Path.class, pathDTO.getId());
            } else {
                path = new Path();
            }
            List<Destination> destinationList = new ArrayList<Destination>();
            for (DestinationDTO destinationDTO : destinationListDTO) {
                Station station = new Station(destinationDTO.getStationId(), destinationDTO.getStationName());
                destinationList.add(new Destination(destinationDTO.getNumber(), station, destinationDTO.getTime(), path));
            }
            path.setDestination(destinationList);
            rDAO.addEntity(path);
        } catch (CreateException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindException ex) {
            Logger.getLogger(PathService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void delete(Long pathNumber) {
        try {
            Path path = (Path)rDAO.findByPrimaryKey(Path.class, pathNumber);
            rDAO.remove(path);
        } catch (RemoveException ex) {
            Logger.getLogger(PathService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindException ex) {
            Logger.getLogger(PathService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
