package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.model.Pathmap;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.service.StationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "/stations")
public class StationController {
    
    @Autowired
    @Qualifier(value = "stationService")
    private StationService stationService;
    
//    @ModelAttribute("stationList")
//    public List<Station> getStationList() {
//        return stationService.getStationList();
//    }
    
    @RequestMapping("/stations")
    public String listStations(Model model) {
        model.addAttribute("station", new Station());
        model.addAttribute("stationList", stationService.getStationList());
        return "/stations";
    }
    
    @RequestMapping(value = "/addStation", method = RequestMethod.POST)
    public String addStation(@ModelAttribute("station")
    Station station) {
        stationService.createStation(station, null, null);
        return "redirect:/stations";
    }
    
}
