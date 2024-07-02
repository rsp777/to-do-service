package com.pawar.todo.app.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pawar.todo.app.entity.Task;
import com.pawar.todo.app.exception.TaskNotFoundException;
import com.pawar.todo.app.service.TaskService;
import com.pawar.todo.dto.TaskDto;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.PATCH})
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/tasks")
	public List<TaskDto> getAllTasks() {

		List<TaskDto> tasksList = taskService.getAllTasks();
		logger.debug("All Tasks : {}", tasksList);

		return tasksList;

	}
	
	@GetMapping("/tasks/username/{username}")
	public ResponseEntity<List<TaskDto>> getTasksByUsername(@PathVariable String username) {

		try {
			List<TaskDto> tasksList = taskService.getTasksByUsername(username);
			logger.info("All Tasks : {}", tasksList);
			return new ResponseEntity<>(tasksList, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			logger.error(e.getMessage());

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/tasks/id/{id}")
	public ResponseEntity<TaskDto> getTasksById(@PathVariable Long id) {

		TaskDto tasksList = new TaskDto();
		try {
			tasksList = taskService.getByTaskId(id);
			logger.info("All Tasks : {}", tasksList);
			return new ResponseEntity<>(tasksList, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			logger.error(e.getMessage());

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

//	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/tasks/create")
	public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, HttpServletRequest request) {

		try {
			logger.info("New Task : {}", taskDto);
			String authorizationHeader = request.getHeader("Authorization");

			logger.info("Authorization Header: " + authorizationHeader);

			Task newTask = taskService.createTask(taskDto, authorizationHeader);
			logger.info("New Task Created : {}", newTask);
			HttpHeaders headers = new HttpHeaders();
		    headers.add("Access-Control-Allow-Headers", "Authorization");
			return new ResponseEntity<>(newTask, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Task failed to save : " + e.getMessage());
		}
	}

//	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PatchMapping("/tasks/update/{id}")
	public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestParam(required = false) String taskName,
			boolean isCompleted) {

		try {
			
			logger.info("updating Task : {}", id);

			Task updatedTask = taskService.updateTask(id, taskName, isCompleted);
			logger.info("Task Update : {}", updatedTask.toString());
			
			return new ResponseEntity<>(updatedTask, HttpStatus.OK);

		} catch (TaskNotFoundException tnfe) {
			logger.error("Task not found : {}", tnfe.getMessage());

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found: " + tnfe.getMessage());
		} catch (OptimisticLockException ole) {
			logger.error("Optimistic locking error : {}", ole.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Task update conflict: " + ole.getMessage());
		} catch (Exception e) {
			logger.error("Task failed to update : {}", e.getMessage());
//			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Task failed to update: " + e.getMessage());

		}
	}

//	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/tasks/delete")
	public ResponseEntity<?> deleteAllTasks() {

		try {
			taskService.deleteAll();
			logger.info("All Tasks deleted : {}");

			return new ResponseEntity<>("Tasks deleted Successfully", HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("All Task failed to deleted : " + e.getMessage());
		}
	}

//	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/tasks/delete/{id}")
	public ResponseEntity<?> deletebyTaskId(@PathVariable Long id) {

		try {
			logger.info("Task Id: {}", id);

			taskService.deleteTask(id);
			logger.info("Task deleted : {}");

			return new ResponseEntity<>("Task deleted Successfully", HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Task failed to deleted : " + e.getMessage());
		}
	}

}
