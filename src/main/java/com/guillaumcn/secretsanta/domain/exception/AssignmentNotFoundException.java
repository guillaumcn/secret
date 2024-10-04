package com.guillaumcn.secretsanta.domain.exception;

public class AssignmentNotFoundException extends EntityNotFoundException {

    public AssignmentNotFoundException(String uuid) {
        super("Assignment exception", uuid);
    }
}
