package com.vet.vetweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("login")
    public String loginPage() {
        return "views/login";
    }

    @PostMapping("login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response) {

        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return "redirect:/login?error";
            }

            String token = jwtAuthtenticationConfig.getJWTToken(username, userDetails.getAuthorities());
            // El token viene como "Bearer xxx", sacamos solo el valor JWT
            String cookieValue = token.startsWith("Bearer ") ? token.substring(7) : token;

            Cookie cookie = new Cookie("jwt", cookieValue);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/panel";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

}