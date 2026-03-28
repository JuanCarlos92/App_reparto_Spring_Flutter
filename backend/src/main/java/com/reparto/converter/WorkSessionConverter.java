package com.reparto.converter;


import com.reparto.entity.WorkSessionEntity;
import com.reparto.models.dto.WorkSessionDTO;
import com.reparto.models.response.WorkSessionResponse;


public class WorkSessionConverter {

    private WorkSessionConverter() {}

    // Entity → DTO (service)
    public static WorkSessionDTO entityToDTO(WorkSessionEntity entity) {
        return WorkSessionDTO.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate().toString())
                .endDate(entity.getEndDate() != null
                        ? entity.getEndDate().toString()
                        : null)
                .status(entity.getStatus())
                .build();
    }

    // DTO → Response (controller)
    public static WorkSessionResponse dtoToResponse(WorkSessionDTO dto) {
        return WorkSessionResponse.builder()
                .id(dto.getId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .build();
    }
}