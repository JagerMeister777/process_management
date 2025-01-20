package com.example.process.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ユーザー情報
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column()
  private String name;

  /** ログインID */
  @Column(nullable = false, unique = true)
  @NotBlank(message = "ログインIDを入力してください。")
  private String loginId;

  /** パスワード */
  @Column(nullable = false)
  @NotBlank(message = "パスワードを4桁以上で入力してください。")
  private String password;

  /** メールアドレス */
  @Column(nullable = true)
  @NotBlank(message = "メールアドレスを入力してください。")
  private String email;

  /** SSO認証ID */
  @Column(name = "sso_id", unique = true)
  private String ssoId;

  /** 役職・権限 */
  @Column(nullable = false)
  private String role;

  /** ユーザーの有効性 */
  private boolean enabled = false;

  /** ユーザーが作成したプロジェクト */
  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
  private List<Project> createdProjects;

  /** ユーザーが関与しているプロジェクト */
  @ManyToMany(mappedBy = "users")
  private List<Project> projects;

  /** ユーザー作成日 */
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  /** ユーザー情報更新日 */
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /** 自動で作成日と更新日を入力する */
  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  /** 自動で作成日と更新日を入力する */
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}

