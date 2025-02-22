package com.pharell.ticket_booking.Repository;

import com.pharell.ticket_booking.Model.Ticket;
import com.pharell.ticket_booking.Enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status); // Trouver les tickets par statut
    List<Ticket> findByEventId(Long Id);      // Trouver les tickets par événement
    List<Ticket> findByEventIdAndStatus(Long Id, TicketStatus status);
}