package com.example.process.management.service;

import com.example.process.management.entity.Task;
import com.example.process.management.form.TaskForm;
import com.example.process.management.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  private final ProjectService projectService;

  @Transactional
  public void createTask(TaskForm form,Long projectId) {
    Task task = new Task();
    task.setName(form.getName());
    task.setDescription(form.getDescription());
    task.setStartDate(form.getStartDate());
    task.setEndDate(form.getEndDate());
    task.setProject(projectService.findByProjectId(projectId));
    task.setStatus("未完了");
    repository.save(task);
  }
}
