package com.pharell.ticket_booking.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class eventDto {

    private Long id;

    private String code;

    private String name;

    private String location;
    private String spaceName;

    private LocalDate date;
    private LocalTime time;


    private  Long ticketId;
}
