package com.pawar.todo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleAssignmentException extends RuntimeException {
    public RoleAssignmentException(String message, Throwable cause) {
        super(message, cause);
    }
}

