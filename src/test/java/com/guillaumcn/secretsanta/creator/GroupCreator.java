package com.guillaumcn.secretsanta.creator;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GroupCreator {

    public static GroupEntity createGroup(String uuid) {
        return GroupEntity.builder()
                          .uuid(uuid)
                          .build();
    }
}
