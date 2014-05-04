package com.tschool.railwayweb.controller;

import com.tschool.railwayweb.dto.FindTrainFormDTO;
import com.tschool.railwayweb.dto.MsgBox;
import com.tschool.railwayweb.dto.TrainDTO;
import com.tschool.railwayweb.service.StationService;
import com.tschool.railwayweb.service.TrainService;
import com.tschool.railwayweb.util.exception.FindException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FindTrainController {

    @Autowired
    private StationService stationService;
    
    @Autowired
    private TrainService trainService;

    @RequestMapping(method = RequestMethod.GET, value = "findTrains")
    public String initForms() {
        return "findTrains";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/findTrains/getStationNameList")
    @ResponseBody
    public List<String> getStationNameList() {
        return stationService.getStationNameList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findTrains/find")
    @ResponseBody
    public FindTrainFormDTO findTrains(@RequestParam(value = "stationFrom") String stationFrom,
                                       @RequestParam(value = "stationTo") String stationTo, 
                                       @RequestParam(value = "dateString") String dateString,
                                       @RequestParam(value = "mod") String mod) {
        FindTrainFormDTO findTrainFormDTO = new FindTrainFormDTO();
        try {
            List<TrainDTO> trainList = null;
            switch(mod) {
                case "stations":
                    trainList = trainService.getTrainsFitStations(stationFrom, stationTo);
                    break;
                case "date":
                    if (!dateString.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf.parse(dateString);
                        trainList = trainService.getTrainsFitDate(date);
                    }
                    break;
                case "stationsDate":
                    if (!dateString.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf.parse(dateString);
                        trainList = trainService.getTrainsFitStationsDate(date, stationFrom, stationTo);
                    }
                    break;
            }
            findTrainFormDTO.setTrainListDTO(trainList);
        } catch (FindException ex) {
            Logger.getLogger(FindTrainController.class.getName()).log(Level.SEVERE, null, ex);
            MsgBox msgBox = new MsgBox();
            msgBox.setError("No trains found");
            findTrainFormDTO.setMsgBox(msgBox);
        } catch (ParseException ex) {}
        return findTrainFormDTO;
    }
}
