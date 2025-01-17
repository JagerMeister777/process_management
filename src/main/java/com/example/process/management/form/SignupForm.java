package com.example.process.management.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {

  private String loginId;

  private String password;

  private String email;
}
