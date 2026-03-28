package com.reparto.controller;

import com.reparto.models.dto.UserDTO;
import com.reparto.models.request.CreateUserRequest;
import com.reparto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public UserDTO createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
