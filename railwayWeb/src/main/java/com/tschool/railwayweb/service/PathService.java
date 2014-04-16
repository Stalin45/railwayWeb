package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Pathmap;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.RemoveException;
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
    public List<Path> getPathList() {
        List<Path> pathList = null;
        try {
             pathList = rDAO.getEntityList(Path.class);
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pathList;
    }
    
    @Transactional
    public Path getById(Long privateKey) {
        Path path = null;
        try {
            path = (Path) rDAO.findByPrimaryKey(Path.class, privateKey);
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }
    
    @Transactional
    public void createPath(Path path, List<Destination> destinationList) {
        try {
            rDAO.addEntity(path);
            rDAO.addEntityList(destinationList);
        } catch (CreateException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Transactional
    public void delete(Path path) {
        try {
            rDAO.remove(path);
        } catch (RemoveException ex) {
            Logger.getLogger(PathmapService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
