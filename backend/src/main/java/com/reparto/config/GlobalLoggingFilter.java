package com.reparto.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@Component
public class GlobalLoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - startTime;

        // Log de la request
        System.out.println("=== REQUEST ===");
        System.out.println(request.getMethod() + " " + request.getRequestURI());

        Enumeration<String> headers = request.getHeaderNames();
        Collections.list(headers).forEach(name ->
                System.out.println("  " + name + ": " + request.getHeader(name))
        );

        if ("POST".equalsIgnoreCase(request.getMethod()) ||
                "PUT".equalsIgnoreCase(request.getMethod()) ||
                "PATCH".equalsIgnoreCase(request.getMethod())) {
            String body = new String(wrappedRequest.getContentAsByteArray());
            System.out.println("Body: " + body);
        }

        // Log de response
        System.out.println("=== RESPONSE ===");
        System.out.println("Status: " + response.getStatus());
        String responseBody = new String(wrappedResponse.getContentAsByteArray());
        System.out.println("Body: " + responseBody);

        System.out.println("Duration: " + duration + "ms");
        System.out.println("====================");

        // Copiamos el body de nuevo al response para que llegue al cliente
        wrappedResponse.copyBodyToResponse();
    }
}