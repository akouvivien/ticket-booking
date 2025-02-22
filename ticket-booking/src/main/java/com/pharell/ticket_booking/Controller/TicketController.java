package com.pharell.ticket_booking.Controller;

import com.pharell.ticket_booking.Model.Ticket;
import com.pharell.ticket_booking.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Liste des tickets vendus
    @GetMapping("/sold")
    public List<Ticket> getSoldTickets() {
        return ticketService.getSoldTickets();
    }

    // Liste des tickets non vendus
    @GetMapping("/unsold")
    public List<Ticket> getUnsoldTickets() {
        return ticketService.getUnsoldTickets();
    }

    // Liste des tickets utilisés
    @GetMapping("/used")
    public List<Ticket> getUsedTickets() {
        return ticketService.getUsedTickets();
    }

    // Liste des tickets disponibles pour un événement
    @GetMapping("/available/{Id}")
    public List<Ticket> getAvailableTicketsForEvent(@PathVariable Long Id) {
        return ticketService.getAvailableTicketsForEvent(Id);
    }

    @GetMapping("/validate/{qrCode}")
    public ResponseEntity<String> isTicketValid(@PathVariable String qrCode) {
        boolean isValid = ticketService.isTicketValid(qrCode);
        if (isValid) {
            return ResponseEntity.ok("Le ticket est valide.");
        } else {
            return ResponseEntity.badRequest().body("Le ticket est invalide.");
        }
    }

    // Utilise un ticket
    @PostMapping("/use/{qrCode}")
    public ResponseEntity<String> useTicket(@PathVariable String qrCode) {
        ticketService.useTicket(qrCode);
        return ResponseEntity.ok("Ticket utilisé avec succès.");
    }
}