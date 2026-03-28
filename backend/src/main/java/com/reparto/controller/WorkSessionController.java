package com.reparto.controller;

import com.reparto.converter.WorkSessionConverter;
import com.reparto.models.dto.WorkSessionDTO;
import com.reparto.models.request.EndWorkSessionRequest;
import com.reparto.models.request.StartWorkSessionRequest;
import com.reparto.models.request.UpdateWorkSessionRequest;
import com.reparto.models.response.ApiResponse;
import com.reparto.models.response.WorkSessionResponse;
import com.reparto.service.WorkSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-sessions")
public class WorkSessionController {

    private final WorkSessionService workSessionService;

    public WorkSessionController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @GetMapping()
    public ResponseEntity<WorkSessionResponse> active() {

        WorkSessionDTO dto = workSessionService.getActive();

        if (dto == null || dto.getId() == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                WorkSessionConverter.dtoToResponse(dto)
        );
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse> start(
            @RequestBody StartWorkSessionRequest request
    ) {
        return ResponseEntity.ok(workSessionService.start(request));
    }

    @PostMapping("/end")
    public ResponseEntity<ApiResponse> end(
            @RequestBody EndWorkSessionRequest request
    ) {
        return ResponseEntity.ok(workSessionService.end(request));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> update(
            @RequestBody UpdateWorkSessionRequest request
    ) {
        return ResponseEntity.ok(workSessionService.update(request));
    }


}
