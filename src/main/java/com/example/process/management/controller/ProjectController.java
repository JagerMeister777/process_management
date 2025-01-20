package com.example.process.management.controller;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.User;
import com.example.process.management.form.CreateProjectForm;
import com.example.process.management.repository.UserRepository;
import com.example.process.management.service.ProjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  private final UserRepository userRepository;

  @GetMapping("/{id}/list")
  public String projectList(@PathVariable Long id, Model model){
    User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりませんんでした。　ID：" + id));
    List<Project> hasUserProject = user.getProjects();
    model.addAttribute("projectList",hasUserProject);
    return "projects/list";
  }

  @GetMapping("/create")
  public String createProject(Model model,CreateProjectForm form){
    List<User> userList = userRepository.findAll();
    model.addAttribute("userList",userList);
    model.addAttribute("id",1);
    return "projects/create";
  }

  @PostMapping("/{id}/create")
  public String createProject(@PathVariable Long id, @ModelAttribute Project project){
    //ユーザーの検索
    User createdByUser = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりませんでした。　ID：" + id));

    projectService.createProject(project,createdByUser);
    return "redirect:/project/list";
  }

}
