package com.pharell.ticket_booking.Model;


import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pharell.ticket_booking.Enums.TicketStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor

@Where(clause = "status = 'DISPONIBLE'") // Filtre les tickets disponibles
@SQLDelete(sql = "UPDATE tickets SET status = 'SUPPRIME' WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private String assignedZone; // Zone du ticket

    @ManyToOne
    @JoinColumn(name = "events_id") // Clé étrangère dans la table Ticket
    @JsonBackReference
    private Event event;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime scanTime; // Pour garder une trace du dernier scan
}

