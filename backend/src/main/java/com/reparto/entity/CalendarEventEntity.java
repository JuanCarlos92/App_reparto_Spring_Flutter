package com.reparto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agenda_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    private String title;

    private String description;

    private LocalDateTime meetingDate;
}
