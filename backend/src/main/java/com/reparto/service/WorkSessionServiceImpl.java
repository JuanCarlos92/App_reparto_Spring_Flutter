package com.reparto.service;

import com.reparto.converter.WorkSessionConverter;
import com.reparto.entity.UserEntity;
import com.reparto.entity.WorkSessionEntity;
import com.reparto.models.dto.WorkSessionDTO;
import com.reparto.models.request.EndWorkSessionRequest;
import com.reparto.models.request.StartWorkSessionRequest;
import com.reparto.models.request.UpdateWorkSessionRequest;
import com.reparto.models.response.ApiResponse;
import com.reparto.repository.UserRepository;
import com.reparto.repository.WorkSessionRepository;
import com.reparto.config.security.JwtService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class WorkSessionServiceImpl implements WorkSessionService {

    private final WorkSessionRepository workSessionRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public ApiResponse start(StartWorkSessionRequest request) {

        Long userId = jwtService.getAuthenticatedUserId();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        workSessionRepository.findByUserIdAndStatus(userId, "ACTIVE")
                .ifPresent(ws -> {
                    throw new RuntimeException("Active work session already exists");
                });

        WorkSessionEntity session = WorkSessionEntity.builder()
                .startDate(LocalDateTime.parse(request.getStartDate()))
                .status("ACTIVE")
                .user(user)
                .build();

        workSessionRepository.save(session);

        WorkSessionEntity saved = workSessionRepository.save(session);

        WorkSessionDTO dto = WorkSessionConverter.entityToDTO(saved);

        return new ApiResponse(true, "Work session started", dto);
    }

    @Override
    public ApiResponse end(EndWorkSessionRequest request) {

        Long userId = jwtService.getAuthenticatedUserId();

        WorkSessionEntity session = workSessionRepository
                .findByUserIdAndStatus(userId, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("No active work session"));

        session.setEndDate(LocalDateTime.parse(request.getEndDate()));
        session.setStatus("FINISHED");

        workSessionRepository.save(session);

        return new ApiResponse(true, "Work session ended", null);
    }

    @Override
    public ApiResponse update(UpdateWorkSessionRequest request) {

        Long userId = jwtService.getAuthenticatedUserId();

        WorkSessionEntity session = workSessionRepository
                .findByUserIdAndStatusIn(userId, List.of("ACTIVE","PAUSED"))
                .orElseThrow(() -> new RuntimeException("No active work session"));

        if (!isValidStatus(request.getStatus())) {
            throw new RuntimeException("Invalid status: " + request.getStatus());
        }

        session.setStatus(request.getStatus());
        workSessionRepository.save(session);

        return new ApiResponse(true, "Work session updated", null);
    }

    @Override
    public WorkSessionDTO getActive() {

        Long userId = jwtService.getAuthenticatedUserId();

        WorkSessionEntity session = workSessionRepository
                .findByUserIdAndStatusIn(userId, List.of("ACTIVE","PAUSED"))
                .orElse(null);

        return session != null
                ? WorkSessionConverter.entityToDTO(session)
                : null;
    }

    private boolean isValidStatus(String status) {
        return List.of("ACTIVE", "PAUSED", "FINISHED").contains(status);
    }
}
