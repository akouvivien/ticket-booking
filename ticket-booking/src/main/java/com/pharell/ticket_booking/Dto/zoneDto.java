package com.pharell.ticket_booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class zoneDto {
    private Long id;

    private String name;

    private int capacity; // Nombre de places disponibles

    private Long eventId;

}
