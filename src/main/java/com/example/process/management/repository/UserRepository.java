package com.example.process.management.repository;

import com.example.process.management.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByLoginId(String loginId);
}
