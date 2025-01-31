package com.example.process.management.controller;

import com.example.process.management.entity.User;
import com.example.process.management.form.SignupForm;
import com.example.process.management.form.UpdateForm;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  /** PasswordEncoder */
  private final PasswordEncoder passwordEncoder;

  /** ユーザーService */
  private final UserService service;

  /**
   * ユーザー新規登録画面
   * @param form 登録フォーム
   * @return 新規ユーザー登録画面
   */
  @GetMapping("/signup")
  public String signupView(SignupForm form) {
    return "users/signup";
  }

  /**
   * 新規ユーザー登録処理
   * @param user 入力されたフォーム情報
   * @param result エラーメッセージ
   * @return エラーが出た場合、登録画面にエラーを返し、エラーがなければDBへ登録処理を行う（パスワードはハッシュ化）
   */
  @PostMapping("/signup")
  public String signup(@Valid @ModelAttribute("signupForm") User user, BindingResult result,Model model,RedirectAttributes redirectAttributes) {
    Boolean isExistEmail = service.findByEmail(user.getEmail());
    Boolean isExistLoginId = service.findByLoginId(user.getLoginId()).isPresent();

    if (result.hasErrors()){
      return "users/signup";
    }else if(isExistEmail){
      model.addAttribute("message","メールアドレスが既に登録されています。");
      return "users/signup";
    }else{
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      service.resistUser(user);
      redirectAttributes.addFlashAttribute("message","登録が成功しました。");
      return "redirect:/login";
    }
  }

  /**
   * ユーザー情報確認画面
   * @param id ユーザーID（PK)
   * @param model 登録されているユーザー情報
   * @return ユーザー情報確認画面
   */
  @GetMapping("/{id}/info")
  public String userInfoView(@PathVariable("id") Long id, Model model) {
    User userInfo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    model.addAttribute("userInfo",userInfo);
    model.addAttribute("id",id);
    return "users/info";
  }

  /**
   * ユーザー情報の更新画面
   * @param id　ユーザーID（PK)
   * @param model　入力フォームに登録済みの情報をバインド
   * @return ユーザー情報の更新画面
   */
  @GetMapping("/{id}")
  public String updateUserView(@PathVariable("id") Long id, Model model) {
    User userInfo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    UpdateForm updateForm = new UpdateForm();
    updateForm.setName(userInfo.getName());
    updateForm.setLoginId(userInfo.getLoginId());
    updateForm.setEmail(userInfo.getEmail());

    model.addAttribute("updateForm",updateForm);
    model.addAttribute("id",id);
    return "/users/update";
  }

  /**
   * ユーザー情報の更新
   * @param id ログインしているユーザーのID（PK)
   * @param form　入力された情報
   * @param model　エラーメッセージとユーザーID（PK)
   * @return エラーメッセージかユーザー情報確認画面
   */

  @PostMapping("/{id}")
  public String updateUser(@PathVariable("id") Long id, @ModelAttribute UpdateForm form, Model model,
      RedirectAttributes redirectAttributes) {

    // 変更を申請しているユーザーIDで検索
    User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));

    // 入力されたログインIDが存在するか
    Optional<User> existUser = service.findByLoginId(form.getLoginId());

    // 入力されたログインIDが使用できるか
    boolean isDuplicateLoginId = user.getLoginId().equals(form.getLoginId())
        && user.getId().equals(existUser.get().getId());

    //登録用パスワードと確認用パスワードが一致しているか
    boolean confirmPassword = !form.getPassword().isBlank()
        && form.getPassword().equals(form.getConfirmPassword());

    if(!isDuplicateLoginId) {
      model.addAttribute("message","ログインIDは既に使われています。別のログインIDを入力してください");
      model.addAttribute("id",id);
      return "/users/update";

    } else if(!confirmPassword) {
      model.addAttribute("message","登録用パスワードと確認用パスワードが一致しません。");
      model.addAttribute("id",id);
      return "/users/update";

    } else {
      User updateUser = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));

      updateUser.setLoginId(form.getLoginId());
      updateUser.setName(form.getName());
      updateUser.setEmail(form.getEmail());
      updateUser.setPassword(passwordEncoder.encode(form.getPassword()));
      service.updateUser(updateUser);

      redirectAttributes.addFlashAttribute("message","更新が完了しました。");
      redirectAttributes.addFlashAttribute("id",id);

      return "redirect:/users/" + id + "/info";
    }
  }

}
