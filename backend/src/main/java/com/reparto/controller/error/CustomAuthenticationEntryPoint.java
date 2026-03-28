package com.reparto.controller.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reparto.models.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 401: Spring Security
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        System.err.println("ERROR capturado: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        ex.printStackTrace();

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDTO.setError("No autorizado o token inválido.");
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setDate(new Date());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
    }
}
