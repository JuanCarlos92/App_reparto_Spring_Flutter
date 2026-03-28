package com.reparto.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String ref;
    private String name;
    private String price;
    private String weight;
}
