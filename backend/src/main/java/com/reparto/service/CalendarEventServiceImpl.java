package com.reparto.service;

import com.reparto.converter.CalendarEventConverter;
import com.reparto.repository.CalendarEventRepository;
import com.reparto.models.dto.CalendarEventDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;

    @Override
    public List<CalendarEventDTO> getAllEvents() {
        return calendarEventRepository.findAll()
                .stream()
                .map(CalendarEventConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
