package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.dto.MsgBox;
import com.tschool.railwayweb.dto.TicketDTO;
import com.tschool.railwayweb.dto.TrainDTO;
import com.tschool.railwayweb.service.PathService;
import com.tschool.railwayweb.service.TicketService;
import com.tschool.railwayweb.service.TrainService;
import com.tschool.railwayweb.service.TrainTypeService;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.EarlyDepartureException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.IncorrectPassengerException;
import com.tschool.railwayweb.util.exception.NoFreeSeatsException;
import com.tschool.railwayweb.util.exception.SecondTicketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TicketController {
    
    @Autowired
    private TrainService trainService;
    
    @Autowired
    private TrainTypeService trainTypeService;
    
    @Autowired
    private PathService pathService;
    
    @Autowired
    private TicketService ticketService;
    
    @RequestMapping(method = RequestMethod.GET, value = "tickets")
    public String initForm(Model model) {
        try {
            model.addAttribute("trainList", trainService.getTrainListUpToDate());
        } catch (FindException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("error", "No trains found");
        }
        return "tickets";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "tickets/orderTrain/{id}")
    @ResponseBody
    public TrainDTO orderTrain(@PathVariable Long id) {
        return trainService.getById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "tickets/buyTicket")
    @ResponseBody
    public MsgBox buyTicket(@RequestParam(value = "trainId") Long trainId,
                               @RequestParam(value = "name") String name, 
                               @RequestParam(value = "secondName") String secondName,
                               @RequestParam(value = "passport") Long passport) {
        MsgBox msgBox = new MsgBox();
        try {
            TicketDTO ticketDTO = new TicketDTO(trainId,name,secondName,passport);
            ticketService.createTicket(ticketDTO);
            msgBox.setMsg("Successfuly!");
        } catch (FindException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            msgBox.setError("The train doesn't exists!");
        } catch (CreateException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            msgBox.setError("Can't order the ticket!");
        } catch (SecondTicketException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            msgBox.setError(ex.getMessage());
        } catch (EarlyDepartureException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            msgBox.setError(ex.getMessage());
        } catch (IncorrectPassengerException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            msgBox.setError(ex.getMessage());
        } catch (NoFreeSeatsException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            msgBox.setError(ex.getMessage());
        }
        return msgBox;
    }
}
