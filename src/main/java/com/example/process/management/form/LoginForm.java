package com.example.process.management.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

  private String loginId;

  private String password;
}
