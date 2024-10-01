package com.guillaumcn.secretsanta.domain.request;

import com.guillaumcn.secretsanta.model.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateUserRequest {

    @NotBlank
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                         .email(email)
                         .firstName(firstName)
                         .lastName(lastName)
                         .password(password)
                         .build();
    }
}
