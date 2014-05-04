package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.dto.RelationDTO;
import com.tschool.railwayweb.dto.StationDTO;
import com.tschool.railwayweb.dto.StationFormDTO;
import com.tschool.railwayweb.model.Station;
import com.tschool.railwayweb.service.StationService;
import com.tschool.railwayweb.util.exception.DataStoreException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.RemoveException;
import com.tschool.railwayweb.util.exception.TrainHasTickets;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping(value = "/stations")
public class StationController {
    
    @Autowired
    private StationService stationService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stations")
    public String initForm(HttpSession session, Model model) {
        StationDTO station = new StationDTO();
        station.setName("this");
        model.addAttribute("error", session.getAttribute("error"));
        model.addAttribute("msg", session.getAttribute("msg"));
        session.setAttribute("error", null);
        session.setAttribute("msg", null);
        model.addAttribute("station", station);
        model.addAttribute("stationList", stationService.getStationList());
        model.addAttribute("nextStationList", stationService.getStationList());
        model.addAttribute("relationList", new ArrayList<RelationDTO>());
        return "stations";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "stations/addStation")
    public String addStation(HttpSession session, @RequestBody StationFormDTO stationFormDTO) {
        StationDTO station = stationFormDTO.getStation();
        List<RelationDTO> relationList = stationFormDTO.getRelationList();
        String newStationName = stationFormDTO.getNewStationName();
        try {
            stationService.createStation(station, newStationName, relationList);
            session.setAttribute("msg", "successfuly!");
        } catch (TrainHasTickets ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", ex.getMessage());
        } catch (RemoveException ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Error, can't delete station");
        } catch (DataStoreException ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Error in application");
        }
        return "redirect:/stations";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "stations/editStation/{id}")
    @ResponseBody
    public StationFormDTO editStation(@PathVariable Long id) {
        StationDTO stationDTO = (StationDTO) stationService.getById(id);
        List<RelationDTO> relationListDTO = stationService.getRelationList(stationDTO);
        List<StationDTO> nextStationListDTO = stationService.getStationList();
        for (RelationDTO relationDTO : relationListDTO) {
            for (int i = 0; i < nextStationListDTO.size(); i++) {
                if (nextStationListDTO.get(i).getName().equals(relationDTO.getNextStationName())) {
                    nextStationListDTO.remove(nextStationListDTO.get(i));
                }
            }
        }
        StationFormDTO stationFormDTO = new StationFormDTO(stationDTO,
                                                           nextStationListDTO,
                                                           relationListDTO);
        return stationFormDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "stations/deleteStation/{id}")
    public String deleteStation(HttpSession session, @PathVariable Long id) {
        try {
            stationService.delete(id);
        } catch (TrainHasTickets ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", ex.getMessage());
        } catch (RemoveException ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Error, can't delete station");
        } catch (FindException ex) {
            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Error, can't delete station");
        }
        return "redirect:/stations";
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Station.class, "nextStation", new PropertyEditorSupport() {

            public void setAsText(String text) {
                if (text instanceof String) {
                    Long nextStationId = Long.parseLong(text);
                    StationDTO nextStation = (StationDTO) stationService.getById(nextStationId);
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
