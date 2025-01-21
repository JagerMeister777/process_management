package com.example.process.management.controller;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.User;
import com.example.process.management.form.ProjectForm;
import com.example.process.management.service.ProjectService;
import com.example.process.management.service.UserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProjectController {

  /** プロジェクトを管理するService */
  private final ProjectService projectService;

  /** ユーザー情報のService */
  private final UserService userService;

  /**
   * ユーザーの作成したプロジェクトリスト
   * @param id ユーザーID
   * @param model ユーザーが作成したプロジェクトリスト（List型）
   * @return プロジェクトリスト画面
   */
  @GetMapping("/project/list")
  public String getProjectList(Model model){
    return "projects/list";
  }

  /**
   * プロジェクトの新規作成画面
   * @param form 入力フォーム
   * @return プロジェクトの作成画面
   */
  @GetMapping("/project/create")
  public String createProjectView(ProjectForm form,Model model) {
    // TODO 動的にユーザーIDを取得できるようにする
    int userId = 1;
    model.addAttribute("id",userId);
    return "projects/create";
  }

  @PostMapping("/{id}/project/create")
  public String createProject(
      @Valid @ModelAttribute("projectForm") ProjectForm form,
      @PathVariable("id") Long userId,
      Model model,
      BindingResult result) {
    Project project = new Project();
    project.setName(form.getName());
    project.setDescription(form.getDescription());
    project.setStartProject(form.getStartProject());
    project.setEndProject(form.getEndProject());

    //ユーザー情報を取得
    User userInfo = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));

    projectService.createProject(project,userInfo);
    return "redirect:/project/list";
  }
}
