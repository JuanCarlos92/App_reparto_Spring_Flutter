package com.reparto.service;

import com.reparto.repository.ClientRepository;
import com.reparto.converter.ClientConverter;
import com.reparto.models.dto.ClientDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
