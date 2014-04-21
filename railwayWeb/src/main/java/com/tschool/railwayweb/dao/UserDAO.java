package com.tschool.railwayweb.dao;

import com.tschool.railwayweb.model.Role;
import com.tschool.railwayweb.model.User;
import com.tschool.railwayweb.util.exception.CreateException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.ThreadLocalSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUser(String login) {
        List<User> userList = new ArrayList<>();
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.login = :login");
        query.setParameter("login", login);
        userList = query.list();
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    public User registerUser(User user) throws CreateException {
        try {
            sessionFactory.getCurrentSession().persist(user);
            return user;
        } catch (Exception ex) {
            throw new CreateException(ex);
        }
    }
    
    public Role getUserRole(String role) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role r where r.role = :role");
        query.setParameter("role", role);
        return (Role) query.list().get(0);
    }
}
