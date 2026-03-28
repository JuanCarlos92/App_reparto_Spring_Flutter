package com.reparto.service;

import com.reparto.models.request.LoginRequest;
import com.reparto.models.response.LoginResponse;

    public interface AuthService {

        LoginResponse login(LoginRequest request);
    }
