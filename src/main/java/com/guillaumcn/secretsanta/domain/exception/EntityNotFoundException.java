package com.guillaumcn.secretsanta.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entityName, String uuid) {
        super(String.format("%s not found for uuid: %s", entityName, uuid));
    }
}
