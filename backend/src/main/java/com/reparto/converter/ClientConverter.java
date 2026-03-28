package com.reparto.converter;

import com.reparto.entity.ClientEntity;
import com.reparto.models.dto.ClientDTO;

public class ClientConverter {

    private ClientConverter() {}

    public static ClientDTO entityToDTO(ClientEntity entity) {
        if (entity == null) return null;

        return ClientDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .zip(entity.getZip())
                .town(entity.getTown())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}

