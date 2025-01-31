package com.example.process.management.service;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.User;
import com.example.process.management.repository.ProjectRepository;
import com.example.process.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

  /** プロジェクト情報のRepository */
  private final ProjectRepository projectRepository;

  /** ユーザー情報のRepository */
  private final UserRepository userRepository;


  public Optional<Project> findById(Long id) {
    return projectRepository.findById(id);
  }

  public Project findByProjectId(Long id) {
    return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found project"));
  }

  /**
   * プロジェクトの新規作成
   * @param project フォームから受け取ったプロジェクト情報
   */
  @Transactional
  public void createProject(Project project, User user) {
    // projectにuserを設定（プロジェクトを作成したユーザーを設定）
    project.setCreatedBy(user);

    // プロジェクトにユーザーを追加（usersリストにユーザーを追加）
    project.getUsers().add(user);

    // ユーザーにプロジェクトを追加（userのprojectsリストにプロジェクトを追加）
    user.getProjects().add(project);

    // 先にuserを保存しておく（userのprojectsやcreatedProjectsが正しく反映される）
    userRepository.save(user);

    // 最後にプロジェクトを保存
    projectRepository.save(project);
  }
}
