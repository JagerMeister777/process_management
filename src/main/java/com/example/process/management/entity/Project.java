package com.example.process.management.entity;

import jakarta.persistence.*;
import java.util.Optional;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  private Long version = 0L;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String description;

  @ManyToOne()
  @JoinColumn(name = "created_by")
  private User createdBy;

  @ManyToMany()
  @JoinTable(
      name = "project_users",
      joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> users;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<Task> tasks;

}
