package com.example.process.management.controller;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.User;
import com.example.process.management.form.ProjectForm;
import com.example.process.management.service.ProjectService;
import com.example.process.management.service.TaskService;
import com.example.process.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProjectController {

  /** プロジェクトを管理するService */
  private final ProjectService projectService;

  /** ユーザー情報のService */
  private final UserService userService;

  private final TaskService taskService;

  @GetMapping("/users/{userId}/projects/{projectId}")
  public String projectView(@PathVariable("projectId") Long projectId,@PathVariable("userId") Long userId, Model model) {
    model.addAttribute("userId",userId);
    model.addAttribute("projectsList",userService.projectsList(userId));
    model.addAttribute("project",projectService.findByProjectId(projectId));
    model.addAttribute("taskList",taskService.getTaskList(projectId));
    return "home";
  }

  /**
   * プロジェクトの新規作成画面
   * @param form 入力フォーム
   * @return プロジェクトの作成画面
   */
  @GetMapping("/projects/{userId}/create")
  public String createProjectView(@PathVariable ("userId") Long userId, ProjectForm form,Model model) {
    model.addAttribute("id",userId);
    return "projects/create";
  }

  @PostMapping("/projects/{userId}/create")
  public String createProject(
      @Valid @ModelAttribute("projectForm") ProjectForm form,
      @PathVariable("userId") Long userId,
      Model model, RedirectAttributes redirectAttributes) {
    //プロジェクト情報をProjectクラスにバインド
    Project project = new Project();
    project.setName(form.getName());
    project.setDescription(form.getDescription());
    project.setStartProject(form.getStartProject());
    project.setEndProject(form.getEndProject());

    //ユーザー情報を取得
    User userInfo = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
    projectService.createProject(project,userInfo);
    redirectAttributes.addFlashAttribute("message","プロジェクトを作成しました。");
    return "redirect:/home/" + userId ;
  }
}
