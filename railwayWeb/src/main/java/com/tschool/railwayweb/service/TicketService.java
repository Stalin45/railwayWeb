package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.RailwayDAO;
import com.tschool.railwayweb.dto.PassengerDTO;
import com.tschool.railwayweb.dto.TicketDTO;
import com.tschool.railwayweb.model.Passenger;
import com.tschool.railwayweb.model.Ticket;
import com.tschool.railwayweb.model.Train;
import com.tschool.railwayweb.util.exception.CreateException;
import com.tschool.railwayweb.util.exception.EarlyDepartureException;
import com.tschool.railwayweb.util.exception.FindException;
import com.tschool.railwayweb.util.exception.IncorrectPassengerException;
import com.tschool.railwayweb.util.exception.NoFreeSeatsException;
import com.tschool.railwayweb.util.exception.SecondTicketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
    
    @Autowired
    @Qualifier(value = "railwayDAO")
    private RailwayDAO rDAO = new RailwayDAO();
    
    //CONSTRAINT passport
    @Transactional
    public void createTicket(TicketDTO ticketDTO) 
            throws FindException, SecondTicketException, EarlyDepartureException, IncorrectPassengerException, CreateException, NoFreeSeatsException {
        Train train = (Train) rDAO.findByPrimaryKey(Train.class, ticketDTO.getTrainId());
        Passenger passenger = rDAO.getPassengerByPassport(ticketDTO.getPassport());
        if (passenger == null) {
            passenger = new Passenger(ticketDTO.getPassport(),ticketDTO.getName(),ticketDTO.getSecondName());
            rDAO.addEntity(passenger);
        } else {
            String name = ticketDTO.getName();
            String secondName = ticketDTO.getSecondName();
            if (!name.equals(passenger.getName()) || !secondName.equals(passenger.getSecondName())) {
                throw new IncorrectPassengerException("Passenger with the same passport has different name/surname!");
            }
        }
        List<Ticket> ticketList = rDAO.getTicketListByTrain(ticketDTO.getTrainId());
        for (Ticket ticket : ticketList) {
            if (ticket.getPassenger().getPassport().toString().equals(ticketDTO.getPassport().toString())) {
                throw new SecondTicketException("This passenger has already bought the ticket!");
            }
        }
        Calendar actualDate = Calendar.getInstance();
        actualDate.add(Calendar.MINUTE, 10);
        Calendar departureDateTime = Calendar.getInstance();
        List<Train> trainList = rDAO.getEntityList(Train.class);
        Date departureDate = train.getDate();
        Date departureTime = train.getPath().getDestination().get(0).getTime();
        departureDateTime.setTimeInMillis(departureDate.getTime() + departureTime.getTime() + 3 * 60 * 60 * 1000);
        if (actualDate.compareTo(departureDateTime) > 0) {
            throw new EarlyDepartureException("Time, when train will departure, is less than 10 minutes!");
        }
        Integer freeSeats = train.getFreeSeats();
        if (freeSeats == 0) {
            throw new NoFreeSeatsException("The train has no free seats!");
        }
        freeSeats--;
        train.setFreeSeats(freeSeats);
        Ticket ticket = new Ticket(train,null,passenger);
        rDAO.addEntity(ticket);
    }
    
        
    @Transactional
    public List<PassengerDTO> getTicketList(Long trainId) {
        List<PassengerDTO> passengerListDTO = new ArrayList<PassengerDTO>();
        try {
            List<Ticket> ticketList = rDAO.getTicketListByTrain(trainId);
            for (Ticket ticket : ticketList) {
                passengerListDTO.add(new PassengerDTO(ticket.getId(),ticket.getTotalCost(),
                                     ticket.getPassenger().getName(), ticket.getPassenger().getSecondName(), 
                                     ticket.getPassenger().getPassport()));
            }
        } catch (FindException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passengerListDTO;
    }
}
