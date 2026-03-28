package com.reparto.service;

import com.reparto.models.dto.CalendarEventDTO;

import java.util.List;

public interface CalendarEventService {
    List<CalendarEventDTO> getAllEvents();
}
