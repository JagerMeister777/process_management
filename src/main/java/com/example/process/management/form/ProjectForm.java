package com.example.process.management.form;

import com.example.process.management.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ProjectForm {

  private Long id;

  private String name;

  private String description;

  private LocalDateTime startProject;

  private LocalDateTime endProject;


}
