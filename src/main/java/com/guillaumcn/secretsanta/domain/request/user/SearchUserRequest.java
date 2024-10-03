package com.guillaumcn.secretsanta.domain.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SearchUserRequest {

    private String uuid;
    private String email;
    private String lastName;
    private String firstName;
}
