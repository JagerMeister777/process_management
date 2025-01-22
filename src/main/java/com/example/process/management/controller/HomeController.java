package com.example.process.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

  /**
   * ユーザー専用ホーム画面
   * @param id ユーザーID（PK)
   * @param model ユーザーID
   * @return ユーザーホーム画面
   */
  //TODO ユーザーによって表示する情報を変える（プロジェクト情報、通知など）
  @GetMapping("/home/{id}")
  public String view(@PathVariable ("id") Long id, Model model) {
    model.addAttribute("id", id);
    return "home";
  }
}
