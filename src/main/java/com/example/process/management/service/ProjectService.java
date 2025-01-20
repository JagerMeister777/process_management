package com.example.process.management.service;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.User;
import com.example.process.management.repository.ProjectRepository;
import com.example.process.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

  /** プロジェクトのRepository */
  private final ProjectRepository projectRepository;

  @Transactional
  public void createProject(Project project,User user){
    project.setCreatedBy(user);
    project.getUsers().add(user);
    projectRepository.save(project);
  }

}
