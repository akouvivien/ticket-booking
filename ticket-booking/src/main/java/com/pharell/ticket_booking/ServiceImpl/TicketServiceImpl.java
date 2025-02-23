package com.pharell.ticket_booking.ServiceImpl;

import com.pharell.ticket_booking.Dto.ticketDto;
import com.pharell.ticket_booking.Model.Event;
import com.pharell.ticket_booking.Model.Ticket;
import com.pharell.ticket_booking.Enums.TicketStatus;
import com.pharell.ticket_booking.Repository.EventRepository;
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

    @Autowired
    private EventRepository eventRepository;

    // Liste des tickets vendus
    @Override
    public List<Ticket> getSoldTickets() {
        return ticketRepository.findByStatus(TicketStatus.VENDU);
    }

    // Liste des tickets non vendus
    @Override
    public List<Ticket> getUnsoldTickets() {
        return ticketRepository.findByStatus(TicketStatus.DISPONIBLE);
    }

    // Liste des tickets utilisés
    @Override
    public List<Ticket> getUsedTickets() {
        return ticketRepository.findByStatus(TicketStatus.UTILISE);
    }

    // Liste des tickets disponibles pour un événement
    @Override
    public List<Ticket> getAvailableTicketsForEvent(Long eventId) {
        return ticketRepository.findByEventIdAndStatus(eventId, TicketStatus.DISPONIBLE);
    }

    // Vérifier si un ticket est valable
    @Override
    public boolean isTicketValid(String qrCode)  {
        // Log : Début de la validation du ticket
        logger.info("Validation du ticket avec ID : {}", qrCode);

        // Récupérer le ticket depuis la base de données
        Ticket ticket = ticketRepository.findByQrCode(qrCode)
                .orElseThrow(() -> {
                    // Log : Ticket non trouvé
                    logger.error("Ticket non trouvé avec ID : {}", qrCode);
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
    @Override
    public void useTicket(String qrCode) {
        Ticket ticket = ticketRepository.findByQrCode(qrCode).orElseThrow(() -> new RuntimeException("Ticket non trouvé" + qrCode));
        if (ticket.getStatus() != TicketStatus.VENDU) {
            throw new RuntimeException("Le ticket n'est pas valide pour utilisation");
        }
        ticket.setStatus(TicketStatus.UTILISE);
        ticket.setScanTime(LocalDateTime.now());
        // ajout de plusieurs possibilité de verification

        ticketRepository.save(ticket);
    }

    @Override
    public Ticket createTicket(ticketDto ticketDto) {
        // Vérifier si l'événement existe
        Event event = eventRepository.findById(ticketDto.getEventId())
                .orElseThrow(() -> new RuntimeException("L'événement sélectionné n'existe pas"));

        // Valider les données du ticket
        if (ticketDto.getQrCode() == null || ticketDto.getQrCode().isEmpty()) {
            throw new IllegalArgumentException("Le QR Code ne peut pas être vide");
        }
        if (ticketDto.getAssignedZone() == null || ticketDto.getAssignedZone().isEmpty()) {
            throw new IllegalArgumentException("La zone assignée ne peut pas être vide");
        }

        // Créer un nouveau ticket
        Ticket newTicket = new Ticket();
        newTicket.setQrCode(ticketDto.getQrCode());
        newTicket.setAssignedZone(ticketDto.getAssignedZone());
        newTicket.setEvent(event);

        // Sauvegarder le ticket dans la base de données
        return ticketRepository.save(newTicket);
    }

    @Override
    public Ticket updateTicket(Long ticketId, ticketDto ticketDto) {
        // Vérifier si le ticket existe
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé avec l'ID : " + ticketId));

        // Vérifier si l'événement existe (si l'ID de l'événement est modifié)
        if (ticketDto.getEventId() != null) {
            Event event = eventRepository.findById(ticketDto.getEventId())
                    .orElseThrow(() -> new RuntimeException("L'événement sélectionné n'existe pas"));
            existingTicket.setEvent(event);
        }

        // Mettre à jour les champs du ticket
        if (ticketDto.getQrCode() != null && !ticketDto.getQrCode().isEmpty()) {
            existingTicket.setQrCode(ticketDto.getQrCode());
        }
        if (ticketDto.getAssignedZone() != null && !ticketDto.getAssignedZone().isEmpty()) {
            existingTicket.setAssignedZone(ticketDto.getAssignedZone());
        }

        // Sauvegarder les modifications
        return ticketRepository.save(existingTicket);
    }


    @Override
    public void deleteTicket(Long ticketId) {
        // Vérifier si le ticket existe
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé avec l'ID : " + ticketId));

        // Supprimer le ticket
        ticketRepository.delete(ticket);
    }
}