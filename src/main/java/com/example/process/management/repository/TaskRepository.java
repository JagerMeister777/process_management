package com.example.process.management.repository;

import com.example.process.management.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
  List<Task> findByProjectId(Long projectId);
}
