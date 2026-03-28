package com.reparto.converter;

import com.reparto.models.response.LoginResponse;
import com.reparto.models.dto.UserDTO;

public class LoginResponseConverter {

    private LoginResponseConverter() {}

    public static LoginResponse from(String token, UserDTO userDTO) {
        return LoginResponse.builder()
                .token(token)
                .user(userDTO)
                .build();
    }
}
