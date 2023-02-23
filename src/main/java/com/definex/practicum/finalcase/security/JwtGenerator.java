package com.definex.practicum.finalcase.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {
    String jwtSecret = "secret";

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date current = new Date();

        // TODO: Move these to application.properties.expiration overhead and secret.
        Date expiration = new Date(current.getTime() + 70000);
        String jwt = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
        return jwt;
    }

    public String getJwtUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validation(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT");
        }
    }
}
