package com.pawar.todo.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pawar.todo.app.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Modifying
	@Query("update Task set completed=:completed where id=:id")
	Optional<?> updateCompleted(Boolean completed,Long id);
	
	@Modifying
	@Query("update Task set taskName=:taskName where id=:id")
	Optional<?> updateTaskName(String taskName,Long id);
	
	Optional<List<Task>> findByuserName(String username);
	
	
}
