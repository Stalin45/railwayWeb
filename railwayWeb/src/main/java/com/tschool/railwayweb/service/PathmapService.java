package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.model.*;
import com.tschool.railwayweb.util.exception.*;
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
public class PathmapService {
    
    @Autowired
    @Qualifier(value = "railwayDAO")
    RailwayDAO rDAO = new RailwayDAO();
    
    @Transactional
    public List<Pathmap> getPathmapList(Station station) {
        List<Pathmap> pathmapList = null;
        try {
             pathmapList = rDAO.getPathmapList(station);
        } catch (FindException ex) {
            Logger.getLogger(StationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pathmapList;
    }
//    
//    @Transactional
//    public Pathmap getById(Long privateKey) {
//        Pathmap pathmap = null;
//        try {
//            pathmap = (Pathmap) rDAO.findByPrimaryKey(Pathmap.class, privateKey);
//        } catch (FindException ex) {
//            Logger.getLogger(PathmapService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return pathmap;
//    }
//    
//    @Transactional
//    public void delete(Pathmap pathmap) {
//        try {
//            rDAO.remove(pathmap);
//        } catch (RemoveException ex) {
//            Logger.getLogger(PathmapService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}