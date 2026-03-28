package com.reparto.controller;

import com.reparto.models.dto.CalendarEventDTO;
import com.reparto.service.CalendarEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar-events")
@AllArgsConstructor
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    @GetMapping()
    public ResponseEntity<List<CalendarEventDTO>> getAllEvents() {
        List<CalendarEventDTO> events = calendarEventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}
