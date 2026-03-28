package com.reparto.service;

import com.reparto.models.dto.UserDTO;
import com.reparto.models.request.CreateUserRequest;

public interface UserService {

    public UserDTO createUser(CreateUserRequest request);
}
