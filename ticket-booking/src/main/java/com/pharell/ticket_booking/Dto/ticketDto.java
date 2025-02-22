package com.pharell.ticket_booking.Dto;

import com.pharell.ticket_booking.Enums.TicketStatus;

import java.time.LocalDateTime;

public class ticketDto {

    private Long id;

    private String qrCode;

    private TicketStatus status;

    private String assignedZone; // Zone du ticket

    private Long eventId;

    private LocalDateTime scanTime;
}
