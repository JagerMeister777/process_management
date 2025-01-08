package com.example.process.management.controller;

import com.example.process.management.entity.User;
import com.example.process.management.form.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

  @GetMapping("/")
  public String view(LoginForm form) {
    return "login";
  }

  @PostMapping("/")
  public String login(@Valid @ModelAttribute("loginForm") User user, Model model, BindingResult result) {
    if(result.hasErrors()) {
      return "login";
    }else{
      return "redirect:/home";
    }
  }
}
