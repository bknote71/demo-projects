package com.example.onlineJudge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.Executor;

@Configuration
public class AppCommonConfig {

    @Bean
    public ProcessBuilder processBuilder() {
        return new ProcessBuilder();
    }

}
