package com.pharell.ticket_booking.ServiceImpl;

import com.pharell.ticket_booking.Model.Ticket;
import com.pharell.ticket_booking.Enums.TicketStatus;
import com.pharell.ticket_booking.Repository.TicketRepository;
import com.pharell.ticket_booking.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

@Autowired
    private TicketRepository ticketRepository;

    // Liste des tickets vendus
    public List<Ticket> getSoldTickets() {
        return ticketRepository.findByStatus(TicketStatus.VENDU);
    }

    // Liste des tickets non vendus
    public List<Ticket> getUnsoldTickets() {
        return ticketRepository.findByStatus(TicketStatus.DISPONIBLE);
    }

    // Liste des tickets utilisés
    public List<Ticket> getUsedTickets() {
        return ticketRepository.findByStatus(TicketStatus.UTILISE);
    }

    // Liste des tickets disponibles pour un événement
    public List<Ticket> getAvailableTicketsForEvent(Long eventId) {
        return ticketRepository.findByEventIdAndStatus(eventId, TicketStatus.DISPONIBLE);
    }

    // Vérifier si un ticket est valable
    public boolean isTicketValid(Long Id)  {
        // Log : Début de la validation du ticket
        logger.info("Validation du ticket avec ID : {}", Id);

        // Récupérer le ticket depuis la base de données
        Ticket ticket = ticketRepository.findById(Id)
                .orElseThrow(() -> {
                    // Log : Ticket non trouvé
                    logger.error("Ticket non trouvé avec ID : {}", Id);
                    return new RuntimeException("Ticket non trouvé");
                });

        // Vérifier les conditions de validité du ticket
         boolean  isValid = ticket.getStatus() == TicketStatus.VENDU // Le ticket doit être vendu
                && ticket.getScanTime() == null; // Le ticket ne doit pas avoir été utilisé

        // Log : Résultat de la validation
        logger.info("Ticket valide : {}", isValid);

        // Retourner le résultat de la validation
        return isValid;
    }
    // Utiliser un ticket (marquer comme utilisé)
    public void useTicket(Long Id) {
        Ticket ticket = ticketRepository.findById(Id).orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        if (ticket.getStatus() != TicketStatus.VENDU) {
            throw new RuntimeException("Le ticket n'est pas valide pour utilisation");
        }
        ticket.setStatus(TicketStatus.UTILISE);
        ticket.setScanTime(LocalDateTime.now());
        ticketRepository.save(ticket);
    }
}