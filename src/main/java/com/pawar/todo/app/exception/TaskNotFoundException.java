package com.pawar.todo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskNotFoundException extends Exception{
	
	public TaskNotFoundException(Long id) {
        super();
    }
	
	
	public TaskNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TaskNotFoundException(String message, Long id) {
		super(message);
	}
	
	public TaskNotFoundException(String message, String username) {
		super(message);
	}
	
	
}
