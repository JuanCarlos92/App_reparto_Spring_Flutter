package com.reparto.models.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkSessionDTO {
    private Long id;
    private String startDate;
    private String endDate;
    private String status;
}

