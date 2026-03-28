package com.reparto.converter;

import com.reparto.entity.CalendarEventEntity;
import com.reparto.models.dto.CalendarEventDTO;

public class CalendarEventConverter {

    private CalendarEventConverter() {}

    public static CalendarEventDTO entityToDTO(CalendarEventEntity entity) {
        if (entity == null) return null;

        return CalendarEventDTO.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .meetingDate(entity.getMeetingDate())
                .build();
    }
}
