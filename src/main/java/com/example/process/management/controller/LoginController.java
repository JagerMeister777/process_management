package com.example.process.management.controller;

import com.example.process.management.form.LoginForm;
import com.example.process.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザー情報のController
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

  /** ユーザーを管理するService */
  private final UserService service;

  /** passwordEncoder */
  private final PasswordEncoder passwordEncoder;

  /**
   * ログイン画面の表示
   *
   * @param form ログインフォーム
   * @return ログイン画面
   */
  @GetMapping
  public String loginView(LoginForm form) {
    return "users/login";
  }

  /**
   * ログイン処理
   *
   * @param form  入力された情報
   * @param model エラーメッセージ
   * @return 認証成功でホーム画面に繊維
   */
  @PostMapping
  public String login(@Valid @ModelAttribute("loginForm") LoginForm form, Model model) {
    //ユーザー情報を取得
    var userInfo = service.findByLoginId(form.getLoginId());

    //値があり、パスワードが一致するか
    var isPasswordMatching = userInfo.isPresent() &&
        passwordEncoder.matches(form.getPassword(), userInfo.get().getPassword());

    if (!isPasswordMatching) {
      model.addAttribute("errorMsg", "ログインIDとパスワードの組み合わせが間違っています。");
      return "users/login";
    } else {
      service.userEnabledTrue(userInfo.get());
      return "redirect:/home";
    }
  }
}
