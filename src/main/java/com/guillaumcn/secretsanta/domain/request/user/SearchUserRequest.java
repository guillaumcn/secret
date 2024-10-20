package com.guillaumcn.secretsanta.domain.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SearchUserRequest {

    private String email;
    private String lastName;
    private String firstName;
}
