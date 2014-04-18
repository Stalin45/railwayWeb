package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Pathmap;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.service.PathService;
import com.tschool.railwayweb.service.StationService;
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
    
    @ModelAttribute("destination")
    public Destination getDestination() {
        return new Destination();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/paths")
    public String initForm(HttpSession session) {
        Path path = new Path();
        
        session.setAttribute("path", path);
        session.setAttribute("lastNumber", 1);
        session.setAttribute("nextStationList", stationService.getStationList());
        session.setAttribute("destinationList", new ArrayList<Destination>());
        return "paths";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "paths/createPath")
    public String createPath(@ModelAttribute("path") Path path, Model model) {
        List<Destination> destinationList = (List<Destination>) model.asMap().get("destinationList");
        pathService.createPath(path, destinationList);
        return "redirect:/paths";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "paths/pushDestination")
    public String pushDestination(HttpSession session, @ModelAttribute(value = "destination") Destination destination) {
        Integer lastNumber = (Integer) session.getAttribute("lastNumber");
        destination.setNumber(lastNumber);
        
        Station nextStation = destination.getStation();
        
        List<Destination> destinationList = (List<Destination>) session.getAttribute("destinationList");
        destinationList.add(destination);
        
        List<Station> nextStationList = new ArrayList<Station>();
        List<Pathmap> pathmapList = nextStation.getCurrentStations();
        for (Pathmap pathmap: pathmapList) {
            nextStationList.add(pathmap.getNextStation());
        }
        session.setAttribute("destinationList", destinationList);
        session.setAttribute("nextStationList", nextStationList);
        session.setAttribute("lastNumber", ++lastNumber);
        return "paths";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "paths/{action}/{number}")
    public String handleStationAction(HttpSession session, @PathVariable Long number, @PathVariable String action, Model model) {
        Path path = (Path) pathService.getById(number);
        if (action.equalsIgnoreCase("editPath")) {
            List<Destination> destinationList = new ArrayList<Destination>();
            destinationList.addAll(path.getDestination());
            Collections.sort(destinationList);
            //number
            Station lastStation = destinationList.get(destinationList.size() - 1).getStation();
            List<Station> nextStationList = new ArrayList<Station>();
            List<Pathmap> pathmapList = lastStation.getCurrentStations();
            for (Pathmap pathmap : pathmapList) {
                nextStationList.add(pathmap.getNextStation());
            }
            session.setAttribute("nextStationList", nextStationList);
            session.setAttribute("destinationList", destinationList);
            session.setAttribute("path", path);
            Integer lastNumber = destinationList.get(destinationList.size() - 1).getNumber();
            session.setAttribute("lastNumber", ++lastNumber);
            return "paths";
        } else if (action.equalsIgnoreCase("deletePath")) {
            pathService.delete(path);
        }
        return "redirect:/paths";
    }
    
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, "time", new PropertyEditorSupport() {
            
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            
            @Override
            public void setAsText(String text) {
                if (text instanceof String) {
                    try {
                        Date time = (Date) sdf.parse(text);
                        setValue(time);
                    } catch (ParseException ex) {
                        Logger.getLogger(PathController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Date time = (Date) value;
                    return sdf.format(time);
                }
                return null;
            }
        });
        
        binder.registerCustomEditor(Station.class, "station", new PropertyEditorSupport() {

            public void setAsText(String text) {
                if (text instanceof String) {
                    Long nextStationId = Long.parseLong(text);
                    Station nextStation = (Station) stationService.getById(nextStationId);
                    setValue(nextStation);
                }
            }

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Station station = (Station) value;
                    return station.getName();
                }
                return null;
            }
        });
    }
    
}
