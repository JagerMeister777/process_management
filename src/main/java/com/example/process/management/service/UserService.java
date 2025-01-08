package com.example.process.management.service;

import com.example.process.management.entity.User;
import com.example.process.management.repository.UserRepository;
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
}
