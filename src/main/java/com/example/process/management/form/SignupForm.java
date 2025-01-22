package com.example.process.management.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
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
