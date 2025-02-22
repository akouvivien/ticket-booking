package com.pharell.ticket_booking.Repository;

import com.pharell.ticket_booking.Model.Event;
import com.pharell.ticket_booking.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}