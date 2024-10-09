package com.guillaumcn.secretsanta.helper;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AssignmentExceptionHelperTest {

    public static final String SOURCE_USER_UUID = "SOURCE_USER_UUID";
    public static final String TARGET_USER_UUID = "TARGET_USER_UUID";

    @Test
    public void usersCorrespondToException_checkIsException_returnTrue() {
        final List<AssignmentExceptionEntity> assignmentExceptions = buildAssignmentExceptionList();

        UserEntity testedSourceUser = UserEntity.builder()
                                                .uuid(SOURCE_USER_UUID)
                                                .build();
        UserEntity testedTargetUser = UserEntity.builder()
                                                .uuid(TARGET_USER_UUID)
                                                .build();

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertTrue(result);

    }

    @Test
    public void onlySourceUserCorrespondToException_checkIsException_returnFalse() {
        final List<AssignmentExceptionEntity> assignmentExceptions = buildAssignmentExceptionList();

        UserEntity testedSourceUser = UserEntity.builder()
                                                .uuid(SOURCE_USER_UUID)
                                                .build();
        UserEntity testedTargetUser = UserEntity.builder()
                                                .uuid("NOT_TARGET_USER_UUID")
                                                .build();

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertFalse(result);

    }

    @Test
    public void onlyDestinationUserCorrespondToException_checkIsException_returnFalse() {
        final List<AssignmentExceptionEntity> assignmentExceptions = buildAssignmentExceptionList();

        UserEntity testedSourceUser = UserEntity.builder()
                                                .uuid("NOT_SOURCE_USER_UUID")
                                                .build();
        UserEntity testedTargetUser = UserEntity.builder()
                                                .uuid(TARGET_USER_UUID)
                                                .build();

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertFalse(result);

    }

    @Test
    public void noUserCorrespondToException_checkIsException_returnFalse() {
        final List<AssignmentExceptionEntity> assignmentExceptions = buildAssignmentExceptionList();

        UserEntity testedSourceUser = UserEntity.builder()
                                                .uuid("NOT_SOURCE_USER_UUID")
                                                .build();
        UserEntity testedTargetUser = UserEntity.builder()
                                                .uuid("NOT_TARGET_USER_UUID")
                                                .build();

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertFalse(result);

    }

    private static List<AssignmentExceptionEntity> buildAssignmentExceptionList() {
        AssignmentExceptionEntity assignmentException = buildAssignmentException();
        return Collections.singletonList(assignmentException);
    }

    private static AssignmentExceptionEntity buildAssignmentException() {
        UserEntity exceptionSourceUser = UserEntity.builder()
                                                   .uuid(SOURCE_USER_UUID)
                                                   .build();
        UserEntity exceptionTargetUser = UserEntity.builder()
                                                   .uuid(TARGET_USER_UUID)
                                                   .build();
        return AssignmentExceptionEntity.builder()
                                        .sourceUser(exceptionSourceUser)
                                        .targetUser(exceptionTargetUser)
                                        .build();
    }
}
