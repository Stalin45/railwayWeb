package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.dto.PassengerDTO;
import com.tschool.railwayweb.dto.TrainTypeDTO;
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
public class TrainTypeService {
    
    @Autowired
    @Qualifier(value = "railwayDAO")
    private RailwayDAO rDAO = new RailwayDAO();
    
    @Transactional
    public List<TrainTypeDTO> getTrainTypeList() {
        List<TrainTypeDTO> trainTypeListDTO = new ArrayList<TrainTypeDTO>();
        try {
            List<TrainType> trainTypeList = rDAO.getEntityList(TrainType.class);
            for (TrainType trainType : trainTypeList) {
                trainTypeListDTO.add(new TrainTypeDTO(trainType.getId(), trainType.getType(),
                                                      trainType.getCostMultiplier(), trainType.getMaxSeats()));
            }
        } catch (FindException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trainTypeListDTO;
    }
    
    @Transactional
    public TrainTypeDTO getById(Long privateKey) {
        TrainTypeDTO trainTypeDTO = null;
        try {
            TrainType trainType = (TrainType) rDAO.findByPrimaryKey(TrainType.class, privateKey);
            trainTypeDTO = new TrainTypeDTO(trainType.getId(), trainType.getType(),
                                            trainType.getCostMultiplier(), trainType.getMaxSeats());
        } catch (FindException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trainTypeDTO;
    }
    
    @Transactional
    public void addTrainType(TrainTypeDTO trainTypeDTO) {
        try {
            TrainType trainType = null;
            if (trainTypeDTO.getId() != null) {
                trainType = (TrainType) rDAO.findByPrimaryKey(TrainType.class, trainTypeDTO.getId());
            } else {
                trainType = new TrainType();
            }
            trainType.setType(trainTypeDTO.getType());
            trainType.setCostMultiplier(trainTypeDTO.getCostMultiplier());
            trainType.setMaxSeats(trainTypeDTO.getMaxSeats());
            rDAO.addEntity(trainType);
        } catch (CreateException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Transactional
    public void delete(Long trainTypeId) {
        try {
            TrainType trainType = (TrainType) rDAO.findByPrimaryKey(TrainType.class, trainTypeId);
            rDAO.remove(trainType);
        } catch (RemoveException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FindException ex) {
            Logger.getLogger(TrainTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 }