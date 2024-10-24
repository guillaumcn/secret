package com.guillaumcn.secretsanta.creator;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCreator {

    public static UserEntity createUser(String uuid) {
        return UserEntity.builder()
                         .uuid(uuid)
                         .build();
    }

    public static UserEntity createUser(String uuid, List<GroupEntity> userGroups) {
        return UserEntity.builder()
                         .uuid(uuid)
                         .groups(userGroups)
                         .build();
    }
}
