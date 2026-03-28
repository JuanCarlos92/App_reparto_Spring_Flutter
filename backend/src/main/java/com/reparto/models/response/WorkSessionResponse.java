package com.reparto.models.response;

import lombok.*;

@Data
@Builder
public class WorkSessionResponse {
    private Long id;
    private String startDate;
    private String endDate;
    private String status;
}
