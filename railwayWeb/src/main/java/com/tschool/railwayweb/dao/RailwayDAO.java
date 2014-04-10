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
            sessionFactory.getCurrentSession().merge(entity);
            return entity;
        } catch (Exception ex) {
            throw new UpdateException(ex);
        }
    }
    
    public void addEntityList(List<T> entityList) {
        for(T entity : entityList) {
            sessionFactory.getCurrentSession().persist(entity);
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
    
    public List<T> getEntityList(Class<T> classToFind) throws FindException {
        String queryString = "FROM " + classToFind.getSimpleName();
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
