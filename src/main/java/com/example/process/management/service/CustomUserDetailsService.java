//package com.example.process.management.service;
//
//import com.example.process.management.entity.User;
//import com.example.process.management.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//  private final UserRepository repository;
//
//  @Override
//  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//    User user = repository.findByLoginId(loginId)
//        .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + loginId));
//
//    return org.springframework.security.core.userdetails.User.builder()
//        .username(user.getLoginId())
//        .password(user.getPassword())
//        .roles(user.getRole())
//        .disabled(!user.isEnabled())
//        .build();
//  }
//}
