package com.reparto.converter;

import com.reparto.entity.UserEntity;
import com.reparto.models.dto.UserDTO;

public class UserConverter {

    private UserConverter() {
    }

    public static UserDTO entityToDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserDTO.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }
}
