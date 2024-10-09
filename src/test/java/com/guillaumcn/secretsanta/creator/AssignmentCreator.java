package com.guillaumcn.secretsanta.creator;

import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssignmentCreator {

    public static final String SOURCE_USER_UUID = "SOURCE_USER_UUID";
    public static final String TARGET_USER_UUID = "TARGET_USER_UUID";

    public static AssignmentEntity createAssignment(String uuid) {
        return AssignmentEntity.builder()
                               .uuid(uuid)
                               .sourceUser(createUser(SOURCE_USER_UUID))
                               .targetUser(createUser(TARGET_USER_UUID))
                               .build();
    }
}
