package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.dto.DestinationDTO;
import com.tschool.railwayweb.dto.PassengerDTO;
import com.tschool.railwayweb.dto.PathDTO;
import com.tschool.railwayweb.dto.TrainDTO;
import com.tschool.railwayweb.dto.TrainFormDTO;
import com.tschool.railwayweb.dto.TrainTypeDTO;
import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Relation;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.model.Train;
import com.tschool.railwayweb.model.TrainType;
import com.tschool.railwayweb.service.PathService;
import com.tschool.railwayweb.service.StationService;
import com.tschool.railwayweb.service.TicketService;
import com.tschool.railwayweb.service.TrainService;
import com.tschool.railwayweb.service.TrainTypeService;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "/stations")
public class TrainController {
    
    @Autowired
    private TrainService trainService;
    
    @Autowired
    private TrainTypeService trainTypeService;
    
    @Autowired
    private PathService pathService;
    
    @Autowired
    private TicketService ticketService;
    
    @RequestMapping(method = RequestMethod.GET, value = "trains")
    public String initFormTrains(Model model) {
        model.addAttribute("trainTypeList", trainTypeService.getTrainTypeList());
        model.addAttribute("trainList", trainService.getTrainList());
        model.addAttribute("pathList", pathService.getPathList());
        return "trains";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "trains/addTrain")
    public String addTrain(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "trainTypeId") Long trainTypeId,
                            @RequestParam(value = "trainTypeType") String trainTypeType,
                            @RequestParam(value = "pathId") Long pathId,
                            @RequestParam(value = "dateString") String dateString) {
        TrainDTO trainDTO = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            trainDTO = new TrainDTO(id, trainTypeId,trainTypeType,pathId,date);
            trainService.addTrain(trainDTO);
            return "redirect:/trains";
        } catch (ParseException ex) {
            Logger.getLogger(TrainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/trains";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "trains/editTrain/{id}")
    @ResponseBody
    public TrainDTO editTrain(@PathVariable Long id) {
        return (TrainDTO) trainService.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "trains/deleteTrain/{id}")
    public String deleteStation(@PathVariable Long id) {
        trainService.delete(id);
        return "redirect:/trains";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "trains/describePath/{id}")
    @ResponseBody
    public List<DestinationDTO> describePath(@PathVariable Long id) {
        return pathService.getDestinationList(new PathDTO(id));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "trains/describeTrainType/{id}")
    @ResponseBody
    public TrainTypeDTO describeTrainType(@PathVariable Long id) {
        return trainTypeService.getById(id);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "trains/viewPassengers/{id}")
    @ResponseBody
    public List<PassengerDTO> viewPassengers(@PathVariable Long id) {
        return ticketService.getTicketList(id);
    }
}
