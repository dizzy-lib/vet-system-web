package com.vet.vetweb;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {
  @GetMapping("/")
  public String landing() {
    return "pages/landing";
  }

  @GetMapping("/login")
  public String login(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/panel";
    }
    return "views/login";
  }

  @GetMapping("/panel")
  public String panel() {
      return "pages/panel";
  }
  
}
