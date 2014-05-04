package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.dto.TrainTypeDTO;
import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Relation;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.model.TrainType;
import com.tschool.railwayweb.service.PathService;
import com.tschool.railwayweb.service.StationService;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "/stations")
public class TrainTypeController {
    
    @Autowired
    private TrainTypeService trainTypeService;
    
    @ModelAttribute("trainTypeList")
    public List<TrainTypeDTO> getTrainTypeList() {
        return trainTypeService.getTrainTypeList();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "trainTypes")
    public String initForm(Model model) {
        model.addAttribute("trainType", new TrainTypeDTO());
        //model.addAttribute("trainTypeList", trainTypeService.getTrainTypeList());
        return "trainTypes";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "trainTypes/addTrainType")
    public String addTrainType(@ModelAttribute("trainType") TrainTypeDTO trainTypeDTO) {
        trainTypeService.addTrainType(trainTypeDTO);
        return "redirect:/trainTypes";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "trainTypes/{action}/{number}")
    public String handleStationAction(@PathVariable Long number, @PathVariable String action, Model model) {
        TrainTypeDTO trainType = (TrainTypeDTO) trainTypeService.getById(number);
        if (action.equalsIgnoreCase("editType")) {
            model.addAttribute("trainType", trainType);
            return "trainTypes";
        } else if (action.equalsIgnoreCase("deleteType")) {
            trainTypeService.delete(number);
        }
        return "redirect:/trainTypes";
    }
}
