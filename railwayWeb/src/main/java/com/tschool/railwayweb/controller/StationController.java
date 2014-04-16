package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.model.Pathmap;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.model.StationDTO;
import com.tschool.railwayweb.service.StationService;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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
//@SessionAttributes({"pathmap", "station"})
public class StationController {
    
    @Autowired
    @Qualifier(value = "stationService")
    private StationService stationService;
    
    @ModelAttribute("stationList")
    public List<Station> getStationList() {
        return stationService.getStationList();
    }
//    
//    @ModelAttribute("pathmapList") 
//    public List<Pathmap> getPathmapList(HttpSession session) {
//        if (session.getAttribute("pathmapList") == null) {
//            List<Pathmap> pathmapList = new ArrayList<Pathmap>();
//            session.setAttribute("pathmapList", pathmapList);
//            return pathmapList;
//        } else 
//            return (List<Pathmap>) session.getAttribute("pathmapList");
//    }
//    
//    @ModelAttribute("nextStationList") 
//    public List<Station> getNextStationList(HttpSession session) {
//        if (session.getAttribute("nextStationList") == null) {
//            List<Station> nextStationList = new ArrayList<Station>();
//            session.setAttribute("nextStationList", stationService.getStationList());
//            return nextStationList;
//        } else 
//            return (List<Station>) session.getAttribute("nextStationList");
//    }
    
    @ModelAttribute("pathmap")
    public Pathmap getPathmap() {
        return new Pathmap();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/stations")
    public String initForm(HttpSession session, Model model) {
        Station station = new Station();
        station.setName("this");
        
        session.setAttribute("station", station);
        session.setAttribute("nextStationList", stationService.getStationList());
        session.setAttribute("pathmapList", new ArrayList<Pathmap>());
        return "stations";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "stations/addStation")
    public String addStation(@ModelAttribute("station") Station station) {
        stationService.createStation(station, null, null);
        return "redirect:/stations";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "stations/bindRelation")
    public String bindRelation(HttpSession session, @ModelAttribute(value = "pathmap") Pathmap pathmapForward, Model model) {
        Pathmap pathmapBack = new Pathmap();
        Station currentStation = (Station) session.getAttribute("station");
        Station nextStation = pathmapForward.getNextStation();
        Float cost = pathmapForward.getCost();
        
        pathmapForward.setCurrentStation(currentStation);
        pathmapBack.setCurrentStation(nextStation);
        pathmapBack.setNextStation(currentStation);
        pathmapBack.setCost(cost);
        
        List<Pathmap> pathmapList = (List<Pathmap>) session.getAttribute("pathmapList");
        pathmapList.add(pathmapForward);
        pathmapList.add(pathmapBack);
        
        List<Station> nextStationList = (List<Station>) session.getAttribute("nextStationList");
        nextStationList.remove(nextStation);
        
        session.setAttribute("pathmapList", pathmapList);
        session.setAttribute("nextStationList", nextStationList);
        
//        model.addAttribute("nextStationList", nextStationList);
        return "stations";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "stations/{action}/{id}")
    public String handleStationAction(HttpSession session, @PathVariable Long id, @PathVariable String action, Model model) {
        Station station = (Station) stationService.getById(id);
        if (action.equalsIgnoreCase("editStation")) {
            List<Pathmap> pathmapList = new ArrayList<Pathmap>();
            pathmapList.addAll(station.getCurrentStations());
            pathmapList.addAll(station.getNextStations());
            
            List<Station> nextStationList = stationService.getStationList();
            for (Pathmap pathmap: pathmapList) {
                if (nextStationList.contains(pathmap.getNextStation())) 
                    nextStationList.remove(pathmap.getNextStation());
            }
            model.addAttribute("nextStationList", nextStationList);
            session.setAttribute("nextStationList", nextStationList);
            session.setAttribute("pathmapList", pathmapList);
            session.setAttribute("station", station);
            return "stations";
        } else if (action.equalsIgnoreCase("deleteStation")) {
            stationService.delete(station);//.delete(project);
        }
        return "redirect:/stations";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "stations/deleteRealtion/{stationCurrentSelector}/{stationNextSelector}")
    public String deleteNewRelation(HttpSession session, @PathVariable String stationCurrentSelector, @PathVariable String stationNextSelector, Model model) {
        List<Pathmap> pathmapList = (List<Pathmap>) session.getAttribute("pathmapList");
        List<Station> nextStationList = (List<Station>) session.getAttribute("nextStationList");
        Station relationsOwner = (Station) session.getAttribute("station");
        for(Iterator<Pathmap> iterator = pathmapList.iterator(); iterator.hasNext();) {
            Pathmap pathmap = iterator.next();
            Station stationCurrent = pathmap.getCurrentStation();
            Station stationNext = pathmap.getNextStation();
            if ((stationCurrentSelector.equals(stationCurrent.getName()) && stationNextSelector.equals(stationNext.getName())) ||
                (stationCurrentSelector.equals(stationNext.getName()) && stationNextSelector.equals(stationCurrent.getName()))) {
                    if (!relationsOwner.getName().equals(stationNext.getName())) {
                        nextStationList.add(stationNext);
                    }
                    iterator.remove();
                    //stationService.delete(pathmap);
            }
        }
        session.setAttribute("nextStationList", nextStationList);
        model.addAttribute("nextStationList", nextStationList);
        session.setAttribute("pathmapList", pathmapList);
        model.addAttribute("pathmapList", pathmapList);
        return "stations";
    }
    
//    @RequestMapping(method = RequestMethod.GET, value = "stations/deleteRelation/{pathmap}")
//    public String deleteNewRelation(HttpSession session, @PathVariable Pathmap pathmap) {
//        List<Pathmap> pathmapList = (List<Pathmap>) session.getAttribute("pathmapList");
//        String stationCurrentSelector = pathmap.getCurrentStation().getName();
//        String stationNextSelector = pathmap.getCurrentStation().getName();
//        for(Pathmap pathmap : pathmapList) {
//            Station stationCurrent = pathmap.getCurrentStation();
//            Station stationNext = pathmap.getNextStation();
//            if ((stationCurrentSelector.equals(stationCurrent) && stationNextSelector.equals(stationNext)) ||
//                (stationCurrentSelector.equals(stationNext) && stationNextSelector.equals(stationCurrent))) {
//                    if (!"this".equals(stationNext)) {
//                        List<Station> nextStationList = (List<Station>) session.getAttribute("nextStationList");
//                        nextStationList.add(stationNext);
//                        session.setAttribute("nextStationList", nextStationList);
//                    }
//                    stationService.delete(pathmap);
//            }
//        }
//        return "stations";
//    }
    
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Station.class, "nextStation", new PropertyEditorSupport() {

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
