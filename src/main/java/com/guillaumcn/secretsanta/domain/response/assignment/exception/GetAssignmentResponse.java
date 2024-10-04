package com.guillaumcn.secretsanta.domain.response.assignment.exception;

import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import com.guillaumcn.secretsanta.domain.response.note.GetNoteResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
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
public class GetAssignmentResponse {

    private String uuid;
    private GetUserResponse sourceUser;
    private GetUserResponse targetUser;
    private GetGroupResponse group;
    private List<GetNoteResponse> notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
