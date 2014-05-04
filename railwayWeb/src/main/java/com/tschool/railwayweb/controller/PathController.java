package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.dto.DestinationDTO;
import com.tschool.railwayweb.dto.PathDTO;
import com.tschool.railwayweb.dto.PathFormDTO;
import com.tschool.railwayweb.dto.RelationDTO;
import com.tschool.railwayweb.dto.StationDTO;
import com.tschool.railwayweb.model.Destination;
import com.tschool.railwayweb.model.Path;
import com.tschool.railwayweb.model.Relation;
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
import org.springframework.format.annotation.DateTimeFormat;
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
public class PathController {
    
    @Autowired
    @Qualifier(value = "pathService")
    private PathService pathService;
    
    @Autowired
    @Qualifier(value = "stationService")
    private StationService stationService;
    
    @RequestMapping(method = RequestMethod.GET, value = "/paths")
    public String initForm(HttpSession session) {
        PathDTO path = new PathDTO();
        
        session.setAttribute("path", path);
        session.setAttribute("pathList", pathService.getPathList());
        session.setAttribute("lastNumber", 1);
        session.setAttribute("nextStationList", stationService.getStationList());
        session.setAttribute("destinationList", new ArrayList<DestinationDTO>());
        return "paths";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "paths/addPath")
    public String addPath(HttpSession session) {
        PathDTO path = (PathDTO) session.getAttribute("path");
        List<DestinationDTO> destinationList = (List<DestinationDTO>) session.getAttribute("destinationList");
        pathService.createPath(path, destinationList);
        return "redirect:/paths";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "paths/pushDestination")
    @ResponseBody
    public PathFormDTO pushDestination(HttpSession session, @RequestParam(value = "stationId") Long stationId,
                                                               @RequestParam(value = "stationName") String stationName,
                                                               @RequestParam(value = "timeString") String timeString) {
        try {
            Integer lastNumber = (Integer) session.getAttribute("lastNumber");
            PathDTO path = (PathDTO) session.getAttribute("path");
            
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            Date time = sdf.parse(timeString);
            
            DestinationDTO destinationDTO = new DestinationDTO(lastNumber,path.getId(),stationId,stationName,time);
            
            List<DestinationDTO> destinationListDTO = (List<DestinationDTO>) session.getAttribute("destinationList");
            destinationListDTO.add(destinationDTO);
            
            StationDTO station = new StationDTO(destinationDTO.getStationId(),destinationDTO.getStationName());
            
            List<StationDTO> nextStationListDTO = stationService.getRelatedStations(station);
            
            session.setAttribute("destinationList", destinationListDTO);
            session.setAttribute("nextStationList", nextStationListDTO);
            session.setAttribute("lastNumber", ++lastNumber);
            
            PathFormDTO pathFormDTO = new PathFormDTO(path,
                    nextStationListDTO,
                    destinationListDTO);
            return pathFormDTO;
        } catch (ParseException ex) {
            Logger.getLogger(PathController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "paths/editPath/{id}")
    @ResponseBody
    public PathFormDTO editPath(HttpSession session, @PathVariable Long id) {
        PathDTO pathDTO = (PathDTO) pathService.getById(id);
        List<DestinationDTO> destinationListDTO = pathService.getDestinationList(pathDTO);
        List<StationDTO> nextStationListDTO = stationService.getStationList();
        for (DestinationDTO destinationDTO : destinationListDTO) {
//                if (nextStationList.contains(relation.getNextStation()))
            for (int i = 0; i < nextStationListDTO.size(); i++) {
                ////////////////////////////////////////////////////////////////////////////////////////////
                if (nextStationListDTO.get(i).getName().equals(destinationDTO.getStationName())) {
                    nextStationListDTO.remove(nextStationListDTO.get(i));
                }
            }
        }
        session.setAttribute("lastNumber", destinationListDTO.size()+1);
        session.setAttribute("nextStationList", nextStationListDTO);
        session.setAttribute("destinationList", destinationListDTO);
        session.setAttribute("path", pathDTO);
        PathFormDTO pathFormDTO = new PathFormDTO(pathDTO,
                                                  nextStationListDTO,
                                                  destinationListDTO);
        return pathFormDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "paths/deletePath/{id}")
    public String deletePath(HttpSession session, @PathVariable Long id) {
        pathService.delete(id);
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

//            public void setAsText(String text) {
//                if (text instanceof String) {
//                    Long nextStationId = Long.parseLong(text);
//                    Station nextStation = (Station) stationService.getById(nextStationId);
//                    setValue(nextStation);
//                }
//            }

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
