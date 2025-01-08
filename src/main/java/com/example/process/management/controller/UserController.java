package com.example.process.management.controller;

import com.example.process.management.entity.User;
import com.example.process.management.form.ResistForm;
import com.example.process.management.repository.UserRepository;
import com.example.process.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

  /** ユーザー登録Service */
  private final UserService service;

  @GetMapping("/user/resist")
  public String view(ResistForm form) {
    return "resister";
  }

  @PostMapping("/user/resist")
  public String addUser(@Valid @ModelAttribute("resistForm") User user, BindingResult result) {
    if (result.hasErrors()){
      return "resister";
    }else{
      service.resistUser(user);
      return "redirect:/home";
    }
  }
}
