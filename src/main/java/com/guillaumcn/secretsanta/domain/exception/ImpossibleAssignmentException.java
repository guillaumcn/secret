package com.guillaumcn.secretsanta.domain.exception;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImpossibleAssignmentException extends Exception {

    public ImpossibleAssignmentException(UserEntity user) {
        super(String.format("Assignment is impossible for user: %s", user.getEmail()));
    }
}
