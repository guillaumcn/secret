package com.guillaumcn.secretsanta.domain.response.user;

import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<GetGroupResponse> groups;
}
