package com.pharell.ticket_booking.Service;

import com.pharell.ticket_booking.Model.Ticket;
import com.pharell.ticket_booking.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface  TicketService {

    // Liste des tickets vendus
     List<Ticket> getSoldTickets();

    // Liste des tickets non vendus
    List<Ticket> getUnsoldTickets() ;

    // Liste des tickets utilisés
    List<Ticket> getUsedTickets();

    // Liste des tickets disponibles pour un événement
    List<Ticket> getAvailableTicketsForEvent(Long Id) ;

    // Vérifier si un ticket est valable
    boolean isTicketValid(Long Id) ;

    // Utiliser un ticket (marquer comme utilisé)
     void useTicket(Long Id);
}