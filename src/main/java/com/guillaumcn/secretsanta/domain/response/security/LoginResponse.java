package com.guillaumcn.secretsanta.domain.response.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginResponse {

    private String token;
    private Long expiresIn;
}

