package com.reparto.service;

import com.reparto.repository.TicketRepository;
import com.reparto.converter.TicketConverter;
import com.reparto.models.dto.TicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
