package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Pathmap;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.service.PathService;
import com.tschool.railwayweb.service.StationService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "/stations")
//@SessionAttributes({"pathmap", "station"})
public class PathController {
    
    @Autowired
    @Qualifier(value = "pathService")
    private PathService pathService;
    
    @Autowired
    @Qualifier(value = "stationService")
    private StationService stationService;
    
    @ModelAttribute("pathList")
    public List<Path> getPathList() {
        return pathService.getPathList();
    }
    
//    @ModelAttribute("station")
//    public Station getStation() {
//        return new Station();
//    } 
    
    @RequestMapping(method = RequestMethod.GET, value = "/paths")
    public String initForm(Model model) {
        model.addAttribute("path", new Path());
        model.addAttribute("destination", new Destination());
        model.addAttribute("destinationList", new ArrayList<Destination>());
        model.addAttribute("nextStationList", stationService.getStationList());
        return "paths";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "paths/createPath")
    public String createPath(@ModelAttribute("path") Path path, Model model) {
        List<Destination> destinationList = (List<Destination>) model.asMap().get("destinationList");
        pathService.createPath(path, destinationList);
        return "redirect:/paths";
    }
    
//    @RequestMapping(method = RequestMethod.POST, value = "/pushDestination")
//    public String addRelation(HttpServletRequest request, Model model) {
//        Pathmap pathmap = new Pathmap(); 
//        Station nextStation = (Station) model.asMap().get("station");
//        Float cost = (Float) request.getAttribute("cost");
//        pathmap.setCurrentStation(nextStation);
//        pathmap.setNextStation((Station) request.getAttribute("nextStation"));
//        pathmap.setCost(cost);
//        List<Pathmap> pathmapList = (List<Pathmap>) model.asMap().get("pathmapList");
//        pathmapList.add(pathmap);
//        model.addAttribute("pathmapList", pathmapList);
//        return "stations";
//    }
    
    @RequestMapping(method = RequestMethod.POST, value = "paths/pushDestination")
    public String pushDestination(@ModelAttribute(value = "destination") Destination destination, Model model) {
        List<Destination> destinationList = (List<Destination>) model.asMap().get("destinationList");
        List<Station> nextStationList = new ArrayList<Station>();
        destinationList.add(destination);
        //
        List<Pathmap> pathmapList = destination.getStation().getCurrentStations();
        for(Pathmap pathmap : pathmapList) {
            nextStationList.add(pathmap.getNextStation());
        }
        model.addAttribute("destinationList", destinationList);
        model.addAttribute("nextStationList", nextStationList);
        return "paths";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "paths/{action}/{id}")
    public String handleAction(@PathVariable Long id, @PathVariable String action, Model model) {
        Path path = (Path) pathService.getById(id);
        if (action.equalsIgnoreCase("edit")) {
//            model.addAttribute("destinationList", pathService.getPathmapList());
//            model.addAttribute("station", station);
            return "paths";
        } else if (action.equalsIgnoreCase("delete")) {
           // .delete(project);
        }
        return "paths";
    }
    
}
