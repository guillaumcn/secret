package com.guillaumcn.secretsanta.creator;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssignmentExceptionCreator {

    public static final String SOURCE_USER_UUID = "SOURCE_USER_UUID";
    public static final String TARGET_USER_UUID = "TARGET_USER_UUID";

    public static List<AssignmentExceptionEntity> createAssignmentExceptionListWithOneUser() {
        AssignmentExceptionEntity assignmentException = createAssignmentException();
        return Collections.singletonList(assignmentException);
    }

    private static AssignmentExceptionEntity createAssignmentException() {
        return AssignmentExceptionEntity.builder()
                                        .sourceUser(createUser(SOURCE_USER_UUID))
                                        .targetUser(createUser(TARGET_USER_UUID))
                                        .build();
    }

    public static AssignmentExceptionEntity createAssignmentException(UserEntity sourceUser, UserEntity targetUser) {
        return AssignmentExceptionEntity.builder()
                                        .sourceUser(sourceUser)
                                        .targetUser(targetUser)
                                        .build();
    }
}
