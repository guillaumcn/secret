package com.guillaumcn.secretsanta.domain.response.assignment.exception;

import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignmentExceptionResponse {

    private GetUserResponse sourceUser;
    private GetUserResponse targetUser;
    private GetGroupResponse group;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
