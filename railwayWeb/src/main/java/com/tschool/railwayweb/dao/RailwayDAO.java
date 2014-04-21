package com.tschool.railwayweb.dao;

import com.tschool.railwayweb.model.*;
import com.tschool.railwayweb.util.exception.*;

import java.io.Serializable;
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
    
    /**
     * creates entity
     *
     * @param entity
     * @return
     * @throws CreateException
     */
    public T addEntity(T entity) throws CreateException {
        try {
            sessionFactory.getCurrentSession().persist(entity);
            return entity;
        } catch (Exception ex) {
            throw new CreateException(ex);
        }
    }
 
    /**
     * updates entity
     *
     * @param entity
     * @return
     * @throws UpdateException
     */
    public T update(T entity) throws UpdateException {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
            return entity;
        } catch (Exception ex) {
            throw new UpdateException(ex);
        }
    }
    
    /**
     * detaches entity.
     * @param entity
     * @throws DataStoreException
     */
    public void detach(T entity) throws DataStoreException {
        try {
            if (sessionFactory.getCurrentSession().contains(entity)) {
                sessionFactory.getCurrentSession().evict(entity);
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        } catch (Exception e) {
            throw new DataStoreException(e);
        }
    }
    
    public void addEntityList(List<T> entityList) {
        for(T entity : entityList) {
            //sessionFactory.getCurrentSession().persist(entity);
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
        }
    }
    
     /**
     * removes entity but it refreshes it before.
     * Refreshing is required because entity could be detached/or composed out of transaction.
     * @param obj
     * @throws RemoveException
     */
    public void remove(T entity) throws RemoveException {
        try {
            sessionFactory.getCurrentSession().refresh(entity);
            sessionFactory.getCurrentSession().delete(entity);
        } catch (Exception e) {
            throw new RemoveException(e);
        }
    }
    
    public void deleteRelationsByStation(Station station) throws RemoveException {
        try {
            //sessionFactory.getCurrentSession().clear();
            for (int i=0; i<station.getCurrentStations().size(); i++) {
                Pathmap relation = station.getCurrentStations().get(i);
                //sessionFactory.getCurrentSession().refresh(relation);
                sessionFactory.getCurrentSession().delete(relation);
            }
            for (int i=0; i<station.getNextStations().size(); i++) {
                Pathmap relation = station.getNextStations().get(i);
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
    //????
    public List<Pathmap> getPathmapList(Station station) throws FindException {
        String queryString = "FROM Pathmap p WHERE p.currentStation = " + station.getId();
        try {
            List<Pathmap> pathmapList = sessionFactory.getCurrentSession().createQuery(queryString).list();
            //for(Pathmap pathmap : pathmapList)
            for (int i=0; i<pathmapList.size(); i++)
                Hibernate.initialize(pathmapList.get(i).getNextStation());
            return pathmapList;
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
    
    public List<T> getPathmapList() throws FindException {
        String queryString = "FROM Pathmap p ORDER BY p.currentStation";
        try {
            return sessionFactory.getCurrentSession().createQuery(queryString).list();
        } catch (Exception ex) {
            throw new FindException(ex);
        }
    }
    
//    public List<TimeTable> whichTimetablesFitPathsDate(Date date, List<Path> pathList) throws FindException {
//        StringBuilder queryString = new StringBuilder("SELECT t FROM TimeTable t WHERE date = :date AND (");
//        
//        for (int i = 0; i<pathList.size(); i++) {
//            queryString.append("path = :path"+i);
//            if (pathList.size() != i+1)
//                 queryString.append(" or ");
//            else queryString.append(")");
//        }
//        
//        Query query = eManager.createQuery(queryString.toString());
//        query.setParameter("date", date);
//        
//        for (int i = 0; i<pathList.size(); i++) {
//            query.setParameter("path"+i, pathList.get(i));
//        }
//        try {
//            return query.getResultList();
//        } catch (Exception ex) {
//            throw new FindException(ex);
//        }
//    }
//    
//    public List<Path> whichPathsFitStations(List<Path> pathsToTest, String stationFrom, String stationTo) throws FindException{
//        List<Path> resultList = new ArrayList<Path>();
//        Boolean isStartIn;
//        
//        for(Path path : pathsToTest) {
//            isStartIn = false;
//            List<Destination> destinationList = path.getDestination();
//            for(Destination destination : destinationList) {
//                if (stationFrom.equals(destination.getStation().getName())) {
//                    isStartIn = true;
//                    continue;
//                }
//                if (isStartIn && stationTo.equals(destination.getStation().getName())) {
//                    resultList.add(path);
//                    break;
//                }
//            }
//        }
//        if (resultList.size() == 0)
//            throw new FindException("No routes fit your options");
//        return resultList;
//    } 
    
}
