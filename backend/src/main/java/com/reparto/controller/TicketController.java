package com.reparto.controller;

import com.reparto.service.TicketService;
import com.reparto.models.dto.TicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping()
    public ResponseEntity<List<TicketDTO>> getAllTicketsBasicInfo() {
        List<TicketDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
}
