package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.model.Pathmap;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.service.PathmapService;
import com.tschool.railwayweb.service.StationService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/pathmaps")
public class PathmapController {
    
    @Autowired
    @Qualifier(value = "pathmapService")
    private PathmapService pathmapService;
 
    @Autowired
    @Qualifier(value = "stationService")
    private StationService stationService;
    
    @ModelAttribute("stationList")
    public List<Station> getStationList() {
        return stationService.getStationList();
    }
//    
//    @ModelAttribute("pathmapList")
//    public List<Pathmap> getStationList() {
//        return pathmapService.getPathmapList();
//    }
//    
    @RequestMapping(method = RequestMethod.GET)//, value = "/")
    public String initForm(Model model) {
        model.addAttribute("pathmap", new Pathmap());
        model.addAttribute("pathmapList", new ArrayList<Pathmap>());
        model.addAttribute("nextStationList", stationService.getStationList());
        return "pathmaps";
    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{action}/{id}")
//    public String handleAction(@PathVariable Long id, @PathVariable String action, Model model) {
//        Pathmap pathmap = (Pathmap) pathmapService.getById(id);
//        if (action.equalsIgnoreCase("edit")) {
//            model.addAttribute("pathmap", pathmap);
//            return "pathmaps";
//        } else if (action.equalsIgnoreCase("delete")) {
//            pathmapService.delete(pathmap);
//        }
//        return "pathmaps";
//    }
    
}
