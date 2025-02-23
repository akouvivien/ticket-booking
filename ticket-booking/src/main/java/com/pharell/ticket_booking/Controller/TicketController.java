package com.pharell.ticket_booking.Controller;

import com.pharell.ticket_booking.Dto.ticketDto;
import com.pharell.ticket_booking.Model.Ticket;
import com.pharell.ticket_booking.Service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Ticket API", description = "API pour gérer les tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Liste des tickets vendus
    @Operation(summary = "Tickets vendu", description = "liste des tickets vendus")
    @GetMapping("/sold")
    public List<Ticket> getSoldTickets() {
        return ticketService.getSoldTickets();
    }

    // Liste des tickets non vendus
    @Operation(summary = "Tickets non vendu", description = "Liste des tickets non vendus")
    @GetMapping("/unsold")
    public List<Ticket> getUnsoldTickets() {
        return ticketService.getUnsoldTickets();
    }

    // Liste des tickets utilisés
    @Operation(summary = "Tickets utilisés", description = "Liste des tickets utilisés")
    @GetMapping("/used")
    public List<Ticket> getUsedTickets() {
        return ticketService.getUsedTickets();
    }

    // Liste des tickets disponibles pour un événement
    @Operation(summary = "Tickets par evenements", description = "Liste des tickets disponibles pour un événement")
    @GetMapping("/available/{Id}")
    public List<Ticket> getAvailableTicketsForEvent(@PathVariable Long Id) {
        return ticketService.getAvailableTicketsForEvent(Id);
    }

    @Operation(summary = "verification tickets", description = "effectue des verifications sur les tickets")
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
    @Operation(summary = "scan un ticket", description = "operation pour le scan des tickets")
    @PostMapping("/use/{qrCode}")
    public ResponseEntity<String> useTicket(@PathVariable String qrCode) {
        ticketService.useTicket(qrCode);
        return ResponseEntity.ok("Ticket utilisé avec succès.");
    }

    // Utilise un ticket
    @Operation(summary = "creer un ticket", description = "creation des tickets")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody ticketDto ticketDto) {
        Ticket createdTicket = ticketService.createTicket(ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // Utilise un ticket
    @Operation(summary = "Modification du ticket", description = "Operation de modification des tickets")
    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long ticketId, @RequestBody ticketDto ticketDto) {
        Ticket updatedTicket = ticketService.updateTicket(ticketId, ticketDto);
        return ResponseEntity.ok(updatedTicket);
    }

    // Utilise un ticket
    @Operation(summary = "suppression du ticket", description = "Operation de suppression des tickets")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}