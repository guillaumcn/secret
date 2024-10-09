package com.guillaumcn.secretsanta.helper;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import org.junit.Test;

import java.util.List;

import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.SOURCE_USER_UUID;
import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.TARGET_USER_UUID;
import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.createAssignmentExceptionListWithOneUser;
import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AssignmentExceptionHelperTest {

    @Test
    public void usersCorrespondToException_checkIsException_returnTrue() {
        final List<AssignmentExceptionEntity> assignmentExceptions = createAssignmentExceptionListWithOneUser();

        UserEntity testedSourceUser = createUser(SOURCE_USER_UUID);
        UserEntity testedTargetUser = createUser(TARGET_USER_UUID);

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertTrue(result);

    }

    @Test
    public void onlySourceUserCorrespondToException_checkIsException_returnFalse() {
        final List<AssignmentExceptionEntity> assignmentExceptions = createAssignmentExceptionListWithOneUser();

        UserEntity testedSourceUser = createUser(SOURCE_USER_UUID);
        UserEntity testedTargetUser = createUser("NOT_TARGET_USER_UUID");

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertFalse(result);

    }

    @Test
    public void onlyDestinationUserCorrespondToException_checkIsException_returnFalse() {
        final List<AssignmentExceptionEntity> assignmentExceptions = createAssignmentExceptionListWithOneUser();

        UserEntity testedSourceUser = createUser("NOT_SOURCE_USER_UUID");
        UserEntity testedTargetUser = createUser(TARGET_USER_UUID);

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertFalse(result);

    }

    @Test
    public void noUserCorrespondToException_checkIsException_returnFalse() {
        final List<AssignmentExceptionEntity> assignmentExceptions = createAssignmentExceptionListWithOneUser();

        UserEntity testedSourceUser = createUser("NOT_SOURCE_USER_UUID");
        UserEntity testedTargetUser = createUser("NOT_TARGET_USER_UUID");

        boolean result = AssignmentExceptionHelper.isException(testedSourceUser, testedTargetUser, assignmentExceptions);

        assertFalse(result);

    }
}
