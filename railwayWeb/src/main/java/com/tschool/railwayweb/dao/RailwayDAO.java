package com.tschool.railwayweb.dao;

import com.tschool.railwayweb.model.*;
import com.tschool.railwayweb.util.exception.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RailwayDAO <T extends Serializable> {

    @Autowired
    private SessionFactory sessionFactory;

    public T addEntity(T entity) throws CreateException {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
            return entity;
        } catch (Exception ex) {
            throw new CreateException(ex);
        }
    }
 
//    /**
//     * updates entity
//     *
//     * @param entity
//     * @return
//     * @throws UpdateException
//     */
//    public T update(T entity) throws UpdateException {
//        try {
//            sessionFactory.getCurrentSession().saveOrUpdate(entity);
//            return entity;
//        } catch (Exception ex) {
//            throw new UpdateException(ex);
//        }
//    }
    
//    /**
//     * detaches entity.
//     * @param entity
//     * @throws DataStoreException
//     */
//    public void detach(T entity) throws DataStoreException {
//        try {
//            if (sessionFactory.getCurrentSession().contains(entity)) {
//                sessionFactory.getCurrentSession().evict(entity);
//                sessionFactory.getCurrentSession().flush();
//                sessionFactory.getCurrentSession().clear();
//            }
//        } catch (Exception e) {
//            throw new DataStoreException(e);
//        }
//    }
    
    public void addEntityList(List<T> entityList) {
        for(T entity : entityList) {
            //sessionFactory.getCurrentSession().persist(entity);
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
        }
    }
    
    public void remove(T entity) throws RemoveException {
        try {
            sessionFactory.getCurrentSession().delete(entity);
        } catch (Exception e) {
            throw new RemoveException(e);
        }
    }
    
    public void refresh(T entity) throws RemoveException {
        sessionFactory.getCurrentSession().refresh(entity);
    }
    
    public void deleteRelationsByStation(Station station) throws RemoveException {
        try {
            //sessionFactory.getCurrentSession().clear();
            for (int i=0; i<station.getCurrentStations().size(); i++) {
                Relation relation = station.getCurrentStations().get(i);
                //sessionFactory.getCurrentSession().refresh(relation);
                sessionFactory.getCurrentSession().delete(relation);
            }
            for (int i=0; i<station.getNextStations().size(); i++) {
                Relation relation = station.getNextStations().get(i);
                sessionFactory.getCurrentSession().delete(relation);
            }
        } catch (Exception e) {
            throw new RemoveException(e);
        }
    }
    
    
    public T findByPrimaryKey(Class<T> entityToFind, Long privateKey) throws FindException{
        T entity = null;
        try {
            entity = (T) sessionFactory.getCurrentSession().get(entityToFind, privateKey);
        } catch (Exception ex) {
            throw new FindException(ex);
        }
        if (entity == null) {
            throw new FindException(entityToFind.getSimpleName() + " with id/name "
                    + privateKey + " not found");
        }
        return entity;
    }
    
    public Passenger getPassengerByPassport(Long passport) {
        String queryString = "FROM Passenger p WHERE p.passport = " + passport;
        try {
            Passenger passenger = (Passenger) sessionFactory.getCurrentSession().createQuery(queryString).list().get(0);
            return passenger;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<Relation> getRelationList(Long stationId) throws FindException {
        String queryString = "FROM Relation p WHERE p.currentStation = " + stationId + " or p.nextStation = " + stationId;
        try {
            List<Relation> relationList = sessionFactory.getCurrentSession().createQuery(queryString).list();
            return relationList;
        } catch (Exception ex) {
            throw new FindException(ex);
        }
    }
    
    public List<Destination> getDestinationList(Long pathId) throws FindException {
        String queryString = "FROM Destination d WHERE path = " + pathId;
        try {
            List<Destination> destinationList = sessionFactory.getCurrentSession().createQuery(queryString).list();
            return destinationList;
        } catch (Exception ex) {
            throw new FindException(ex);
        }
    }
    
    public List<Ticket> getTicketListByTrain(Long trainId) throws FindException {
        String queryString = "FROM Ticket d WHERE train = " + trainId;
        try {
            List<Ticket> ticketList = sessionFactory.getCurrentSession().createQuery(queryString).list();
            return ticketList;
        } catch (Exception ex) {
            throw new FindException(ex);
        }
    }
    
    public List<T> getEntityList(Class<T> classToFind) throws FindException {
        String queryString = "FROM " + classToFind.getSimpleName();
        try {
            return sessionFactory.getCurrentSession().createQuery(queryString).list();
        } catch (Exception ex) {
            throw new FindException(ex);
        }
    }
    
    public List<T> getRelationList() throws FindException {
        String queryString = "FROM Relation p ORDER BY p.currentStation";
        try {
            return sessionFactory.getCurrentSession().createQuery(queryString).list();
        } catch (Exception ex) {
            throw new FindException(ex);
        }
    }
    
    public List<Train> whichTrainsFitDate(Date date, List<Train> trainList) throws FindException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String queryString = "FROM Train t WHERE date = :date AND (";
        for (int i=0; i<trainList.size(); i++) {
            queryString += "id = " + trainList.get(i).getId();
            if (trainList.size() != i + 1) {
                queryString += " or ";
            } else {
                queryString += ")";
            }
        }
        return sessionFactory.getCurrentSession().createQuery(queryString).setDate("date", date).list();
    }
    
    
}
