package com.guillaumcn.secretsanta.mapper;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.CreateAssignmentExceptionResponse;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentExceptionResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignmentExceptionMapper {
    public static GetAssignmentExceptionResponse mapToGetAssignmentException(AssignmentExceptionEntity assignmentExceptionEntity) {
        return GetAssignmentExceptionResponse.builder()
                .uuid(assignmentExceptionEntity.getUuid())
                .sourceUser(UserMapper.mapToGetUserResponse(assignmentExceptionEntity.getSourceUser(), false))
                .targetUser(UserMapper.mapToGetUserResponse(assignmentExceptionEntity.getTargetUser(), false))
                .group(GroupMapper.mapToGetGroupResponse(assignmentExceptionEntity.getGroup()))
                .createdAt(assignmentExceptionEntity.getCreatedAt())
                .updatedAt(assignmentExceptionEntity.getUpdatedAt())
                .build();
    }

    public static CreateAssignmentExceptionResponse mapToCreateAssignmentExceptionResponse(AssignmentExceptionEntity assignmentExceptionEntity) {
        return CreateAssignmentExceptionResponse.builder()
                .uuid(assignmentExceptionEntity.getUuid())
                .build();
    }

    public static AssignmentExceptionEntity mapToAssignmentExceptionEntity(UserEntity sourceUser, UserEntity targetUser, GroupEntity group) {
        return AssignmentExceptionEntity.builder()
                .sourceUser(sourceUser)
                .targetUser(targetUser)
                .group(group)
                .build();
    }
}
