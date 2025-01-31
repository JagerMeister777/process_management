package com.example.process.management.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {

  @NotBlank
  private String loginId;

  @NotBlank
  private String name;

  @NotBlank
  private String password;

  @NotBlank
  private String confirmPassword;

  @NotBlank
  private String email;
}
