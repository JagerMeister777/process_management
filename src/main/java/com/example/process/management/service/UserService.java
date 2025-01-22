package com.example.process.management.service;

import com.example.process.management.entity.Project;
import com.example.process.management.entity.User;
import com.example.process.management.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  /** ユーザー情報Repository */
  private final UserRepository repository;

  /**
   * ユーザー情報の検索
   * @param loginId ログインID
   * @return DBに登録されているユーザー情報（なければNullを返す）
   */
  public Optional<User> findByLoginId(String loginId) {
    return repository.findByLoginId(loginId);
  }

  /**
   * ユーザーのID検索
   *
   * @param id プライマリーキー
   * @return ユーザー情報
   */
  public Optional<User> findById(Long id){
    return repository.findById(id);
  }


  /**
   * ユーザーが作成したプロジェクトの取得
   * @param id ユーザーID
   * @return ユーザーが作成したプロジェクトリスト
   */
  public List<Project> showProjectList(long id) {
    User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    return user.getProjects();
  }

  /**
   * ユーザーの登録処理
   * @param user 登録するユーザー情報
   */
  public void resistUser(User user){
    user.setRole("USER");
    user.setEnabled(false);
    repository.save(user);
  }

  /**
   * ユーザーの登録情報を更新
   * @param user ユーザー情報
   */
  public void updateUser(User user) {
    repository.save(user);
  }

  /**
   * ユーザー情報の削除
   * @param id ユーザーID（PK）
   */
  public void deleteUser(Long id) {
    User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    repository.delete(user);
  }

  /**
   * ログイン後の有効化
   * @param user ユーザー情報
   */
  public void userEnabledTrue(User user){
    user.setEnabled(true);
    repository.save(user);
  }
}
