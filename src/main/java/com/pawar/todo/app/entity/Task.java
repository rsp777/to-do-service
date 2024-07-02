package com.pawar.todo.app.entity;

import java.sql.Date;

import com.pawar.todo.dto.TaskDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Long id;

	@Column(name = "task_name", nullable = false)
	private String taskName;
	
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(name = "completed", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private boolean completed;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "due_date")	
	private Date dueDate;

	public Task() {
	}

	public Task(Long id, String taskName,String userName, boolean completed,Date createdAt,Date dueDate) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.userName = userName;
		this.completed = completed;
		this.createdAt = createdAt;
		this.dueDate = dueDate;
	}

	public Task(String taskName,String userName, boolean completed) {
		this.taskName = taskName;
		this.userName = userName;
		this.completed = completed;
	}

	public Task(TaskDto taskDto) {
		this.id = taskDto.getId();
		this.taskName = taskDto.getTaskName();
		this.completed = taskDto.isCompleted();
		this.createdAt = taskDto.getCreatedAt();
		this.dueDate = taskDto.getDueDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", userName=" + userName + ", completed=" + completed
				+ ", createdAt=" + createdAt + ", dueDate=" + dueDate + "]";
	}	
}
