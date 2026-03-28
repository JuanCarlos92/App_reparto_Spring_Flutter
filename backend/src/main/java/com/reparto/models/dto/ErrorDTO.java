package com.reparto.models.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
    private String message;
    private String error;
    private int status;
    private Date date;
}
