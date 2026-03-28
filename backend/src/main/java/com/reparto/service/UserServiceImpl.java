package com.reparto.service;

import com.reparto.converter.UserConverter;
import com.reparto.entity.UserEntity;
import com.reparto.models.dto.UserDTO;
import com.reparto.models.request.CreateUserRequest;
import com.reparto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public UserDTO createUser(CreateUserRequest request) {
        // verificar si existe
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        UserEntity user = UserEntity.builder()
                .login(request.getLogin())
                .password(encoder.encode(request.getPassword()))
                .build();

        UserEntity saved = userRepository.save(user);

        return UserConverter.entityToDTO(saved);
    }
}
