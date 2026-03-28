package com.reparto.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String name;
    private String address;
    private String zip;
    private String town;
    private String phone;
    private String email;
    private Double latitude;
    private Double longitude;
}

