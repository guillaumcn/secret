package com.guillaumcn.secretsanta.service.security;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.security.LoginRequest;
import com.guillaumcn.secretsanta.domain.response.security.LoginResponse;
import com.guillaumcn.secretsanta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                                        .orElseThrow();

        return LoginResponse.builder()
                            .token(jwtService.generateToken(user))
                            .expiresIn(jwtService.getExpirationTime())
                            .build();
    }
}
