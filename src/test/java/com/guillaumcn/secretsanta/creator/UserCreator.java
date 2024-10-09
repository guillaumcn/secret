package com.guillaumcn.secretsanta.creator;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCreator {

    public static UserEntity createUser(String uuid) {
        return UserEntity.builder()
                         .uuid(uuid)
                         .build();
    }
}
