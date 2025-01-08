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

  public void resistUser(User user){
    user.setRole("USER");
    repository.save(user);
  }

  public Optional<User> findByLoginId(String loginId) {
    return repository.findByLoginId(loginId);
  }
}
