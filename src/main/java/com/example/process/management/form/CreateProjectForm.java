package com.example.process.management.form;

import com.example.process.management.entity.User;
import java.util.List;
import lombok.Data;

@Data
public class CreateProjectForm {

  private String name;

  private String description;

  private List<User> users;
}
