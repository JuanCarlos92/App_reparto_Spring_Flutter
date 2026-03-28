package com.reparto.converter;

import com.reparto.entity.TicketEntity;
import com.reparto.models.dto.TicketDTO;

public class TicketConverter {

    private TicketConverter() {}

    public static TicketDTO entityToDTO(TicketEntity entity) {
        if (entity == null) return null;

        return TicketDTO.builder()
                .id(entity.getId())
                .ref(entity.getRef())
                .subject(entity.getSubject())
                .status(entity.getStatus())
                .dateOpen(entity.getDateOpen())
                .build();
    }
}
