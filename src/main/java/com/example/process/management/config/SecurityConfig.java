package com.example.process.management.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Securityの設定クラス。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

    //H2コンソールを認証不要にする
    http
        .authorizeHttpRequests(
        auth -> auth
            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
            .permitAll());

    //H2コンソールのCSRFの無効化
    http
        .csrf(
        csrf -> csrf
            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));

    //セキュリティーヘッダー（これがないとH2コンソールを表示できない）
    http
        .headers(headers -> headers
        .frameOptions(frame -> frame.sameOrigin()));

    //セキュリティー設定
    http
        .securityMatcher("/**")  // すべてのリクエストに適用
        .formLogin(Customizer.withDefaults())  // フォームログインをデフォルト設定で有効化
        .csrf(Customizer.withDefaults())  // CSRF保護をデフォルト設定で有効化
        .headers(Customizer.withDefaults())  // ヘッダー設定をデフォルト設定で有効化
        .authorizeHttpRequests(authz -> authz.anyRequest().authenticated());  // すべてのリクエストに認証を要求

    return http.build();
  }
}
