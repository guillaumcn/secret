package com.guillaumcn.secretsanta.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

    public GroupNotFoundException(String uuid) {
        super("Group", uuid);
    }
}
