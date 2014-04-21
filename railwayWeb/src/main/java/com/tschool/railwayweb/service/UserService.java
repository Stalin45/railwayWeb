package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.UserDAO;
import com.tschool.railwayweb.model.Role;
import com.tschool.railwayweb.model.User;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.SecondUserException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserDAO userDAO;
    
    @Transactional
    public void createUser(User user) throws SecondUserException, CreateException {
        User existedUser = userDAO.getUser(user.getLogin());
        if (existedUser != null) {
            throw new SecondUserException("This user is already registered");
        }
        Role role = userDAO.getUserRole("ROLE_USER");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        user.setRoleList(roleList);
        userDAO.registerUser(user);
    }
}
