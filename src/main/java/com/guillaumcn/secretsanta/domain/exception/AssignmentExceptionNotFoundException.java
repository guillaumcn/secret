package com.guillaumcn.secretsanta.domain.exception;

public class AssignmentExceptionNotFoundException extends EntityNotFoundException {

    public AssignmentExceptionNotFoundException(String uuid) {
        super("Assignment exception", uuid);
    }
}
