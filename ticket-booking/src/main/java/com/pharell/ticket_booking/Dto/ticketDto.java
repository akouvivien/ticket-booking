package com.pharell.ticket_booking.Dto;

import com.pharell.ticket_booking.Enums.TicketStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ticketDto {

    private Long id;

    private String qrCode;

    private TicketStatus status;

    private String assignedZone; // Zone du ticket

    private Long eventId;

    private LocalDateTime scanTime;
}
