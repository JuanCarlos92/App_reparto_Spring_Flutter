package com.reparto.service;

import com.reparto.models.dto.WorkSessionDTO;
import com.reparto.models.request.EndWorkSessionRequest;
import com.reparto.models.request.StartWorkSessionRequest;
import com.reparto.models.request.UpdateWorkSessionRequest;
import com.reparto.models.response.ApiResponse;

public interface WorkSessionService {

    ApiResponse start(StartWorkSessionRequest request);

    ApiResponse end(EndWorkSessionRequest request);

    ApiResponse update(UpdateWorkSessionRequest request);

    WorkSessionDTO getActive();
}
