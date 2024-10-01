package com.guillaumcn.secretsanta.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String uuid) {
        super("User", uuid);
    }
}
