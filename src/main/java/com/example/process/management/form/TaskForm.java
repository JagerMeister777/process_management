package com.example.process.management.form;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskForm {

  private String name;

  private String description;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String status;

  private Long projectId;

  private Long userId;
}
