package com.pharell.ticket_booking.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "logentrys")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LogEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String qrCode;
    private String zone;
    private LocalDateTime entryTime;

    @ManyToOne
    private Event event;
}
