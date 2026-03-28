package com.reparto.service;

import com.reparto.converter.LoginResponseConverter;
import com.reparto.converter.UserConverter;
import com.reparto.models.request.LoginRequest;
import com.reparto.models.response.LoginResponse;
import com.reparto.repository.UserRepository;
import com.reparto.config.security.JwtService;
import com.reparto.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByLogin(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(user.getId(), user.getLogin());

        return LoginResponseConverter.from(token, UserConverter.entityToDTO(user));
    }
}


