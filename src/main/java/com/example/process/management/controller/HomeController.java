package com.example.process.management.controller;

import com.example.process.management.entity.Project;
import com.example.process.management.service.ProjectService;
import com.example.process.management.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {


  private final UserService userService;


  /**
   * ユーザー専用ホーム画面
   * @param userId ユーザーID（PK)
   * @param model ユーザーID
   * @return ユーザーホーム画面
   */
  //TODO ユーザーによって表示する情報を変える（プロジェクト情報、通知など）
  @GetMapping("/home/{userId}")
  public String view(@PathVariable ("userId") Long userId, Model model) {
    Project project = userService.projectsList(userId).getFirst();
    model.addAttribute("projectsList",userService.projectsList(userId));
    model.addAttribute("userId", userId);
    model.addAttribute("project",project);
    return "home";
  }
}
