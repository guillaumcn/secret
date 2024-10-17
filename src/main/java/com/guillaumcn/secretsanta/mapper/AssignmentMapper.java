package com.guillaumcn.secretsanta.mapper;

import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssignmentMapper {

    public static GetAssignmentResponse mapToGetAssignment(AssignmentEntity assignmentEntity, boolean withNotes) {
        return GetAssignmentResponse.builder()
                                    .uuid(assignmentEntity.getUuid())
                                    .sourceUser(UserMapper.mapToGetUserResponse(assignmentEntity.getSourceUser(), false))
                                    .targetUser(UserMapper.mapToGetUserResponse(assignmentEntity.getTargetUser(), false))
                                    .group(GroupMapper.mapToGetGroupResponse(assignmentEntity.getGroup(), false))
                                    .notes(withNotes ? assignmentEntity.getNotes().stream().map(NoteMapper::mapToGetNote).toList() : null)
                                    .createdAt(assignmentEntity.getCreatedAt())
                                    .updatedAt(assignmentEntity.getUpdatedAt())
                                    .build();
    }
}
