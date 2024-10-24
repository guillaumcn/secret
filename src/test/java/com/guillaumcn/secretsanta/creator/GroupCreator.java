package com.guillaumcn.secretsanta.creator;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GroupCreator {

    public static GroupEntity createGroup(String uuid) {
        return GroupEntity.builder()
                          .uuid(uuid)
                          .build();
    }

    public static GroupEntity createGroup(String uuid, List<UserEntity> users) {
        return GroupEntity.builder()
                          .uuid(uuid)
                          .users(users)
                          .build();
    }
}
