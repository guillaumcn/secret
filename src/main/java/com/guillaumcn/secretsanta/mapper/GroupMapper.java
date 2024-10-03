package com.guillaumcn.secretsanta.mapper;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.group.CreateGroupRequest;
import com.guillaumcn.secretsanta.domain.response.group.CreateGroupResponse;
import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupMapper {
    public static GetGroupResponse mapToGetGroupResponse(GroupEntity groupEntity) {
        return GetGroupResponse.builder()
                .uuid(groupEntity.getUuid())
                .name(groupEntity.getName())
                .owner(UserMapper.mapToGetUserResponse(groupEntity.getOwner(), false))
                .build();
    }

    public static CreateGroupResponse mapToCreateGroupResponse(GroupEntity groupEntity) {
        return CreateGroupResponse.builder()
                .uuid(groupEntity.getUuid())
                .build();
    }

    public static GroupEntity mapToGroupEntity(CreateGroupRequest createGroupRequest, UserEntity ownerEntity) {
        return GroupEntity.builder()
                .name(createGroupRequest.getName())
                .owner(ownerEntity)
                .build();
    }
}
