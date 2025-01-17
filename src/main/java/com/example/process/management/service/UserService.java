package com.example.process.management.service;

import com.example.process.management.entity.User;
import com.example.process.management.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  /** ユーザー情報Repository */
  private final UserRepository repository;

  /**
   * ユーザーの登録処理
   * @param user 登録するユーザー情報
   */
  public void resistUser(User user){
    user.setRole("USER");
    repository.save(user);
  }

  /**
   * ユーザー情報の検索
   * @param loginId ログインID
   * @return DBに登録されているユーザー情報（なければNullを返す）
   */
  public Optional<User> findByLoginId(String loginId) {
    return repository.findByLoginId(loginId);
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
