package com.vet.vetweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {
  @GetMapping("/")
  public String landing() {
    return "pages/landing";
  }

  @GetMapping("/login")
  public String login() {
    return "views/login";
  }
}
