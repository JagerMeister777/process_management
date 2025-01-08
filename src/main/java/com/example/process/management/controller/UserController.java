package com.example.process.management.controller;

import com.example.process.management.entity.User;
import com.example.process.management.form.LoginForm;
import com.example.process.management.form.SignupForm;
import com.example.process.management.service.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
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
public class UserController {

  /** ユーザー登録Service */
  private final UserService service;

  /** passwordEncoder */
  private final PasswordEncoder passwordEncoder;

  /**
   * ログイン画面の表示
   * @param form ログインフォーム
   * @return ログイン画面
   */
  @GetMapping("/login")
  public String loginView(LoginForm form) {
    return "login";
  }


  /**
   * ログイン処理
   * @param form 入力された情報
   * @param model エラーメッセージ
   * @return 認証成功でホーム画面に繊維
   */
  @PostMapping("/login")
  public String login(@Valid @ModelAttribute("loginForm") LoginForm form, Model model) {
      //ユーザー情報を取得
      var userInfo = service.findByLoginId(form.getLoginId());

      //値があり、パスワードが一致するか
      var isPasswordMatching = userInfo.isPresent() &&
          passwordEncoder.matches(form.getPassword(),userInfo.get().getPassword());

      if(!isPasswordMatching) {
        model.addAttribute("errorMsg","ログインIDとパスワードの組み合わせが間違っています。");
        return "login";
      }else{
        return "redirect:/home";
      }
  }

  /**
   * ユーザー新規登録画面
   * @param form 登録フォーム
   * @return 新規ユーザー登録画面
   */
  @GetMapping("/user/signup")
  public String signupView(SignupForm form) {
    return "signup";
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
      return "signup";
    }else{
      var encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      service.resistUser(user);
      return "redirect:/home";
    }
  }
}
