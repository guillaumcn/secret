package com.guillaumcn.secretsanta.domain.request.user;

import com.guillaumcn.secretsanta.repository.specification.SearchUserSpecification;
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

    public SearchUserSpecification toSpecification() {
        return SearchUserSpecification.builder()
                .uuid(uuid)
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .build();
    }
}
