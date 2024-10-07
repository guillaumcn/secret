package com.guillaumcn.secretsanta.validator;

import com.guillaumcn.secretsanta.domain.exception.ImpossibleAssignmentException;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.helper.AssignmentExceptionHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssignmentValidator {

    public static void assertAssignationIsPossible(List<UserEntity> users, List<AssignmentExceptionEntity> assignmentExceptions) throws ImpossibleAssignmentException {
        for (UserEntity user : users) {
            for (UserEntity potentialAssignee : users) {
                if (!user.getUuid().equals(potentialAssignee.getUuid()) && !AssignmentExceptionHelper.isException(user, potentialAssignee, assignmentExceptions)) {
                    break;
                }
            }
            throw new ImpossibleAssignmentException(user);
        }
    }
}
