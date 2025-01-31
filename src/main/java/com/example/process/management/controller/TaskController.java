package com.example.process.management.controller;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.Task;
import com.example.process.management.form.TaskForm;
import com.example.process.management.service.ProjectService;
import com.example.process.management.service.TaskService;
import com.example.process.management.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/projects/{projectId}")
public class TaskController {

  private final ProjectService projectService;

  private final TaskService taskService;

  @GetMapping("/task/create")
  public String taskCreateView(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId,
      TaskForm form,Model model) {
    model.addAttribute("projectId",projectId);
    model.addAttribute("userId",userId);
    return "tasks/create";
  }

  @PostMapping("/task/create")
  public String taskCreate(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId, @ModelAttribute TaskForm form,Model model,
      RedirectAttributes redirectAttributes) {
    Optional<Project> existedProject = projectService.findById(projectId);

    if(existedProject.isPresent()) {
      taskService.createTask(form,projectId);
      redirectAttributes.addFlashAttribute("message","タスクを作成しました。");
      return "redirect:/home/" + userId ;
    }else{
      model.addAttribute("errorMsg","プロジェクトが見つかりませんでした。");
      return "tasks/create";
    }
  }
}
