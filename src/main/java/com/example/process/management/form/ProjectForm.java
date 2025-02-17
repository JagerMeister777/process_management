package com.example.process.management.form;

import com.example.process.management.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectForm {

  private String name;

  private String description;

  private LocalDateTime startProject;

  private LocalDateTime endProject;


}
