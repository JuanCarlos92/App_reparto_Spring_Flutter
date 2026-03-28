package com.reparto.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDTO {
    private Long id;
    private String ref;
    private String subject;
    private String status;
    private LocalDateTime dateOpen;
}
