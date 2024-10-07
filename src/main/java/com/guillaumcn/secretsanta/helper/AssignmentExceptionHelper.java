package com.guillaumcn.secretsanta.helper;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssignmentExceptionHelper {

    public static boolean isException(UserEntity sourceUser, UserEntity targetUser, List<AssignmentExceptionEntity> assignmentExceptions) {
        return assignmentExceptions.stream().anyMatch(exception -> exception.getSourceUser().getUuid().equals(sourceUser.getUuid())
                                                                   && exception.getTargetUser().getUuid().equals(targetUser.getUuid()));
    }
}
