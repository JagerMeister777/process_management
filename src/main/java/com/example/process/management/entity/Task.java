package com.example.process.management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Column(name = "start_date")
  private LocalDateTime startDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
