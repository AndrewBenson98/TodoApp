package com.arbenson.taskapi.util.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;


    public String getTokenSubject(String token) { //throws Exception

        //remove "Bearer"
        token = token.substring(7);

//        try{

            JwtParser jwtParser = Jwts.parser().setSigningKey(secret);

            //Parse and validate the token
            Jwt jwtobj = jwtParser.parse(token);

            Claims claims = (Claims)jwtobj.getBody();
            return claims.getSubject();

//        }catch(MalformedJwtException e){
//            throw new ServletException("Token is modified by unauthorized user");
//        }

    }


}
