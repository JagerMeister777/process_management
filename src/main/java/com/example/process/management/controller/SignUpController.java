package com.example.process.management.controller;

import com.example.process.management.entity.User;
import com.example.process.management.form.LoginForm;
import com.example.process.management.form.SignupForm;
import com.example.process.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ユーザー情報のController
 */
@Controller
@RequiredArgsConstructor
public class SignUpController {

  /** ユーザーを管理するService */
  private final UserService service;

  /** passwordEncoder */
  private final PasswordEncoder passwordEncoder;

  /**
   * ユーザー新規登録画面
   * @param form 登録フォーム
   * @return 新規ユーザー登録画面
   */
  @GetMapping("/user/signup")
  public String signupView(SignupForm form) {
    return "users/signup";
  }

  /**
   * 新規ユーザー登録処理
   * @param user 入力されたフォーム情報
   * @param result エラーメッセージ
   * @return エラーが出た場合、登録画面にエラーを返し、エラーがなければDBへ登録処理を行う（パスワードはハッシュ化）
   */
  @PostMapping("/user/signup")
  public String signup(@Valid @ModelAttribute("signupForm") User user, BindingResult result) {
    if (result.hasErrors()){
      return "users/signup";
    }else{
      var encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      service.resistUser(user);
      return "redirect:/login";
    }
  }
}
