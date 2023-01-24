package com.bknote71.muzinsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    private final String[] permitAllResources = {"/accounts/**", "/h2-console/**", "/brands/**", "/products/**", "/api/products/**"};

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(false)
                .ignoring()
                .antMatchers("/h2-console/**", "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers(permitAllResources).permitAll()
//                .antMatchers("/brands/register/**").hasRole("ADMIN")
//                .antMatchers("/products/**").hasRole("PARTNER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler(((request, response, exception) -> {
                    System.out.println(exception);
                    response.sendRedirect("/");
                }));

        return http.build();
    }
}
