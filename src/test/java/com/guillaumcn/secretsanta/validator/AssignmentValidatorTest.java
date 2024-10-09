package com.guillaumcn.secretsanta.validator;

import com.guillaumcn.secretsanta.domain.exception.ImpossibleAssignmentException;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.createAssignmentException;
import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.Assert.assertThrows;

public class AssignmentValidatorTest {

    private static final String USER_UUID_1 = "USER_UUID_1";
    private static final String USER_UUID_2 = "USER_UUID_2";
    private static final String USER_UUID_3 = "USER_UUID_3";

    @Test
    public void onlyOneUser_checkIfAssignationIsPossible_throwException() {
        List<UserEntity> singleUserList = Collections.singletonList(createUser(USER_UUID_1));
        List<AssignmentExceptionEntity> emptyExceptions = Collections.emptyList();
        assertThrows(ImpossibleAssignmentException.class, () -> AssignmentValidator.assertAssignationIsPossible(singleUserList, emptyExceptions));
    }

    @Test
    public void noExceptions_checkIfAssignationIsPossible_doNotThrowException() throws ImpossibleAssignmentException {
        List<UserEntity> userList = List.of(createUser(USER_UUID_1), createUser(USER_UUID_2));
        List<AssignmentExceptionEntity> emptyExceptions = Collections.emptyList();
        AssignmentValidator.assertAssignationIsPossible(userList, emptyExceptions);
    }

    @Test
    public void twoUsersWithMatchingExceptions_checkIfAssignationIsPossible_throwException() {
        UserEntity firstUser = createUser(USER_UUID_1);
        UserEntity secondUser = createUser(USER_UUID_2);
        List<UserEntity> userList = List.of(firstUser, secondUser);
        List<AssignmentExceptionEntity> exceptions = List.of(
                createAssignmentException(firstUser, secondUser)
        );
        assertThrows(ImpossibleAssignmentException.class, () -> AssignmentValidator.assertAssignationIsPossible(userList, exceptions));
    }

    @Test
    public void threeUsersWithOneException_checkIfAssignationIsPossible_throwException() throws ImpossibleAssignmentException {
        UserEntity firstUser = createUser(USER_UUID_1);
        UserEntity secondUser = createUser(USER_UUID_2);
        UserEntity thirdUser = createUser(USER_UUID_3);
        List<UserEntity> userList = List.of(firstUser, secondUser, thirdUser);
        List<AssignmentExceptionEntity> exceptions = List.of(
                createAssignmentException(firstUser, secondUser)
        );
        AssignmentValidator.assertAssignationIsPossible(userList, exceptions);
    }
}
