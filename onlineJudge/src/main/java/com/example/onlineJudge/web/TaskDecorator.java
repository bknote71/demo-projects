package com.example.onlineJudge.web;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface TaskDecorator {
    Runnable decorate(Runnable runnable);

    // interface 메소드는 기본적으로 public abstract
    // default, private, static 제어자로 메소드를 생성할 수 있다.
    // 단, default, static, private 을 사용하려면 내부에 메소드 내용을 정의해야 한다.

    // interface 필드는 무조건 public static final 키워드가 설정
    // private 필드는 설정될 수 없다.
    public static final TaskDecorator EMITTER_HOLDER_TASK_DECORATOR = (runnable) -> {
        final SseEmitter emitter = ProgressEventEmitterHolder.getEmitter();

        return () -> {
            try {
                ProgressEventEmitterHolder.setEmitter(emitter);
                runnable.run();
            } finally {
                ProgressEventEmitterHolder.clearHolder();
            }
        };
    };

}
