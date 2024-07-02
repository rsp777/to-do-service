package com.pawar.todo.app.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pawar.todo.app.entity.Task;
import com.pawar.todo.app.exception.TaskNotFoundException;
import com.pawar.todo.app.exception.UpdateConflictException;
import com.pawar.todo.app.jwtfilter.JwtTokenProvider;
import com.pawar.todo.app.repository.TaskRepository;
import com.pawar.todo.dto.TaskDto;

import jakarta.persistence.OptimisticLockException;

@Service
public class TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	private static final String NEW_USER_TOPIC = "TO.DO.NEW.USER";

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public Task createTask(TaskDto taskDto, String authorizationHeader) {
		
		String userName = jwtTokenProvider.getUsernameFromJWT(authorizationHeader); 
		
		Task task = new Task(taskDto.getTaskName(),userName,taskDto.isCompleted());
		task.setCreatedAt(Date.valueOf(LocalDate.now()));
		task.setDueDate(Date.valueOf(LocalDate.now().plusDays(1)));
		logger.info("Task : {}",task.toString());
		return taskRepository.save(task);
	}

	@Transactional
	public Task updateTask(Long id, String taskName, boolean iscompleted)
			throws TaskNotFoundException, UpdateConflictException {
		Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

		try {

			if (iscompleted != existingTask.isCompleted()) {
				existingTask.setCompleted(iscompleted);
				logger.info("iscompleted : {}", iscompleted);
				taskRepository.updateCompleted(iscompleted, id);

			} else {
				logger.error("Error occurred while updating iscompleted flag");
			}
			existingTask.setTaskName(taskName);
			taskRepository.updateTaskName(taskName, id);
			logger.info("iscompleted : {}", iscompleted);

		} catch (OptimisticLockException ole) {
			// Handle the exception, e.g., log it and inform the user
			throw new UpdateConflictException("The task was updated by another transaction. Please retry.");
		}
		return existingTask;
	}

	public void deleteTask(Long id) throws TaskNotFoundException{
		Task task = taskRepository.findById(id).get();
		taskRepository.delete(task);
	}

	public void deleteAll() {

		taskRepository.deleteAll();

	}

	private Task convertDtoToEntity(TaskDto taskDto) {
		Task task = new Task(taskDto);
		return task;
	}

	private TaskDto convertEntityToDto(Task task) {
		TaskDto taskDto = new TaskDto(task.getId(), task.getTaskName(),task.getUserName(),task.isCompleted(),task.getCreatedAt(),task.getDueDate());
		return taskDto;
	}

	private List<TaskDto> convertEntityToDto(List<Task> tasks) {

		List<TaskDto> taskDtos = new ArrayList<>();

		for (Task task : tasks) {
			TaskDto taskDto = new TaskDto(task.getId(), task.getTaskName(),task.getUserName(),task.isCompleted(),
																		task.getCreatedAt(),task.getDueDate());
			logger.info("taskDto : {}",taskDto);
			taskDtos.add(taskDto);
		}
		logger.info("taskDtos : {}",taskDtos);
		return taskDtos;
	}

	public List<TaskDto> getAllTasks() {
		
		List<TaskDto> taskDtos = convertEntityToDto(taskRepository.findAll());
//		logger.debug("Tasks : {}",taskDtos);
		return taskDtos;
	}

	public TaskDto getByTaskId(Long id) throws TaskNotFoundException{

		TaskDto taskDto = convertEntityToDto(taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(
				"Task Not Found",id)));
		logger.debug("Task : {}",taskDto);
		return taskDto;

	}

	public List<TaskDto> getTasksByUsername(String username) throws TaskNotFoundException {
		
		List<TaskDto> taskDto = convertEntityToDto(taskRepository.findByuserName(username)
								.orElseThrow(() -> new TaskNotFoundException(
								"Task Not Found",username)));
		logger.debug("Task : {}",taskDto);
		return taskDto;

	}
	
//	@KafkaListener(topics = NEW_USER_TOPIC)
//	public void userListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {
//
//		try {
//			String key = consumerRecord.key();
//			String value = consumerRecord.value();
//			int partition = consumerRecord.partition();
//			ObjectMapper mapper = new ObjectMapper();
//			UserDto user = mapper.readValue(value, UserDto.class);
//
//			
//			logger.info("Consumed message : " + user + " with key : " + key + " from partition : " + partition);
//			if (value != null) {
////				userRepository.save(user);
////				logger.info("User saved to Login database : {}", user);
//
//				ack.acknowledge();
//			} else {
//				logger.warn("Received null value from Kafka topic. {}");
//			}
//		} catch (Exception e) {
//			logger.error("Error processing Kafka message: {}", e.getMessage());
//			// Handle the exception (e.g., log, retry, or skip)
//		}
//	}

}
