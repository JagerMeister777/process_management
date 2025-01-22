package com.example.process.management.controller;

import com.example.process.management.entity.User;
import com.example.process.management.form.SignupForm;
import com.example.process.management.form.UpdateForm;
import com.example.process.management.service.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final PasswordEncoder passwordEncoder;

  private final UserService service;

  @GetMapping("/info/{id}")
  public String userInfoView(@PathVariable("id") Long id, Model model) {
    User userInfo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    model.addAttribute("userInfo",userInfo);
    model.addAttribute("id",id);
    return "users/info";
  }

  @GetMapping("/update/{id}")
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

  @PostMapping("/update/{id}")
  public String updateUser(@PathVariable("id") Long id, @ModelAttribute UpdateForm form, Model model) {

    // 変更を申請しているユーザーIDで検索
    User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));

    // 入力されたログインIDが使用できるか
    boolean isDuplicateLoginId = !user.getLoginId().equals(form.getLoginId())
        && user.getId().equals(id);

    //登録用パスワードと確認用パスワードが一致しているか
    boolean confirmPassword = !form.getPassword().isBlank()
        && form.getPassword().equals(form.getConfirmPassword());

    if(!isDuplicateLoginId){
      model.addAttribute("message","ログインIDは既に使われています。別のログインIDを入力してください");
      model.addAttribute("id",id);
      return "/users/update";

    }else if(!confirmPassword){
      model.addAttribute("message","登録用パスワードと確認用パスワードが一致しません。");
      model.addAttribute("id",id);
      return "/users/update";

    }else{
      User updateUser = service.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));

      updateUser.setLoginId(form.getLoginId());
      updateUser.setName(form.getName());
      updateUser.setEmail(form.getEmail());
      updateUser.setPassword(passwordEncoder.encode(form.getPassword()));
      service.updateUser(updateUser);

      model.addAttribute("message","更新が完了しました。");
      model.addAttribute("id",id);

      return "redirect:/user/info" + id;
    }
  }
}
