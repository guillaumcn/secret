package com.guillaumcn.secretsanta.mapper;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.group.CreateGroupRequest;
import com.guillaumcn.secretsanta.domain.response.group.CreateGroupResponse;
import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GroupMapper {

    public static GetGroupResponse mapToGetGroupResponse(GroupEntity groupEntity, boolean withOwner) {
        return GetGroupResponse.builder()
                               .uuid(groupEntity.getUuid())
                               .name(groupEntity.getName())
                               .owner(withOwner ? UserMapper.mapToGetUserResponse(groupEntity.getOwner(), false) : null)
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
