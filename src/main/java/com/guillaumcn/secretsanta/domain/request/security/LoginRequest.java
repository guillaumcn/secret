package com.guillaumcn.secretsanta.domain.request.security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

