package com.guillaumcn.secretsanta.domain.request;

import com.guillaumcn.secretsanta.repository.specification.SearchUserSpecification;
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
