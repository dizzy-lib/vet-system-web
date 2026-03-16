package com.vet.vetweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((request) -> request
            .requestMatchers("/").permitAll()
            .requestMatchers("/css/**.css").permitAll()
            .requestMatchers("/panel/pacientes/registrar").hasRole(Role.ADMIN.name())
            .anyRequest().authenticated())
        .formLogin((form) -> form
            .loginPage("/login")
            .defaultSuccessUrl("/panel", true)
            .permitAll())
        .logout((logout) -> logout.permitAll());

    return http.build();
  }

  @Bean
  @Description("In Memory Userdetails service registered since DB doesn't have user table")
  public UserDetailsService users() {
    // The builder will ensure the passwords are encoded before saving in memory
    UserDetails vet = User.builder()
        .username("vet@vet.cl")
        .password(passwordEncoder().encode("password"))
        .roles(Role.VET.name())
        .build();

    UserDetails admin = User.builder()
        .username("admin@vet.cl")
        .password(passwordEncoder().encode("password"))
        .roles(Role.ADMIN.name())
        .build();

    UserDetails root = User.builder()
        .username("root@vet.cl")
        .password(passwordEncoder().encode("password"))
        .roles(Role.ROOT.name(), Role.ADMIN.name())
        .build();

    return new InMemoryUserDetailsManager(vet, admin, root);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
