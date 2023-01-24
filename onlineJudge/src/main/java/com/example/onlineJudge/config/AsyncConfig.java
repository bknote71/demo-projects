package com.example.onlineJudge.config;

import com.example.onlineJudge.web.ProgressEventEmitterHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Application 이 아닌, Async 설정 클래스에 붙여야 함.
public class AsyncConfig {

    @Bean
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 스레드 수
        taskExecutor.setMaxPoolSize(30); // 최대 스레드 수
        taskExecutor.setQueueCapacity(100); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("Executor-");
        taskExecutor.setTaskDecorator((runnable) -> {
            final SseEmitter emitter = ProgressEventEmitterHolder.getEmitter();

            return () -> {
                try {
                    ProgressEventEmitterHolder.setEmitter(emitter);
                    runnable.run();
                } finally {
                    ProgressEventEmitterHolder.clearHolder();
                }
            };
        });

        return taskExecutor;
    }

}