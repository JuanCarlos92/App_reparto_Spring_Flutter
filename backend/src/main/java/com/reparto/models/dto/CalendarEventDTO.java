package com.reparto.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEventDTO {
    private Long id;
    private Long clientId;
    private String title;
    private String description;
    private LocalDateTime meetingDate;
}
