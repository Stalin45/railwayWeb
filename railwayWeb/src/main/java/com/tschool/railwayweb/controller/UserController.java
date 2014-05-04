package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.model.Relation;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.model.User;
import com.tschool.railwayweb.service.UserService;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.SecondUserException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.GET, value = "registration")
    public String initForm(Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }
    @RequestMapping(method = RequestMethod.POST, value = "registration/newUser")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.createUser(user);
        } catch (SecondUserException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("error", ex.getMessage());
            return "registration";
        } catch (CreateException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("error", ex.getMessage());
            return "registration";
        }
        return "registrationSucced";
    }
}
