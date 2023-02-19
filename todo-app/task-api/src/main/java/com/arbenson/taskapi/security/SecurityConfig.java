package com.arbenson.taskapi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;


    @Bean
    public FilterRegistrationBean getFilter(){

        UrlBasedCorsConfigurationSource urlconfig = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsconfig = new CorsConfiguration();

        List<String> all = Arrays.asList("*");

        //Set CORS configt- give common access for all header types - can be more specific within filter
        corsconfig.setAllowedOriginPatterns(all);
        corsconfig.setAllowedMethods(all);
        corsconfig.setAllowCredentials(true);
        corsconfig.setAllowedHeaders(all);

        //register urlconfig with cors config
        urlconfig.registerCorsConfiguration("/**", corsconfig);

        FilterRegistrationBean fbean = new FilterRegistrationBean(new CorsFilter(urlconfig));
        fbean.setFilter(new TaskFilter(secret));

        //select what endpoints to filter
        fbean.addUrlPatterns("/api/v1/*");


        return fbean;
    }
}
