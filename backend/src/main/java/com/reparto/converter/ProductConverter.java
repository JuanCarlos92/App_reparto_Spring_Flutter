package com.reparto.converter;

import com.reparto.entity.ProductEntity;
import com.reparto.models.dto.ProductDTO;

public class ProductConverter {

    private ProductConverter() {}

    public static ProductDTO entityToDTO(ProductEntity entity) {
        if (entity == null) return null;

        return ProductDTO.builder()
                .id(entity.getId())
                .ref(entity.getRef())
                .name(entity.getName())
                .price(entity.getPrice())
                .weight(entity.getWeight())
                .build();
    }
}

