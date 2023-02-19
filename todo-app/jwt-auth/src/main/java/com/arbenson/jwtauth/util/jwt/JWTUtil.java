package com.arbenson.jwtauth.util.jwt;

import com.arbenson.jwtauth.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;
    private long expiry = 10_000_000;


    /**
     * Generate the JWT token
     * @param user
     * @return
     */
    public String generateToken(User user){

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiry))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

    }
}
