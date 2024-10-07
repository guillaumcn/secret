package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.domain.request.security.LoginRequest;
import com.guillaumcn.secretsanta.domain.response.security.LoginResponse;
import com.guillaumcn.secretsanta.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticate(loginRequest);
    }
}
