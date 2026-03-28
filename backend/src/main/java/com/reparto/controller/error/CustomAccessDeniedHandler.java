package com.reparto.controller.error;

import com.reparto.models.dto.ErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 403: Acceso denegado Error de Token JWT
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        // log
        System.err.println("ERROR capturado: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        ex.printStackTrace();

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.FORBIDDEN.value());
        errorDTO.setError("Acceso denegado, no tienes permiso (verifica el token)");
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDate(new Date());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
    }
}