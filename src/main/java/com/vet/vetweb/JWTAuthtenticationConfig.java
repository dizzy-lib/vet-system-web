package com.vet.vetweb;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vet.vetweb.Constants.*;

@Configuration
public class JWTAuthtenticationConfig {

    public String getJWTToken(String username, Collection<? extends GrantedAuthority> authorities) {



        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1440))
                .and()
                .signWith(getSigningKey(SUPER_SECRET_KEY))
                .compact();

        return "Bearer " + token;
    }
}
