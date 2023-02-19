package com.arbenson.taskapi.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaskFilter extends GenericFilter {

    private String secret;

    public TaskFilter(String secret){
        this.secret=secret;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");


        //handle pre-flight request
        if(httpServletRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{

            String authHeader = httpServletRequest.getHeader("Authorization");

            //If header is null or does not start with "Bearer" throw exception
            if(authHeader==null || !authHeader.startsWith("Bearer ")){
                throw new ServletException("JWT token missing");
            }


            //remove "Bearer" from token
            String token = authHeader.substring(7);

            try{

                JwtParser jwtParser = Jwts.parser().setSigningKey(secret);

                //Parse and validate the token
                Jwt jwtobj = jwtParser.parse(token);

//            Claims claims = (Claims)jwtobj.getBody();

            }catch(MalformedJwtException e){
                throw new ServletException("Token is modified by unauthorized user");
            }

        }
        filterChain.doFilter(servletRequest,servletResponse);


    }

}
