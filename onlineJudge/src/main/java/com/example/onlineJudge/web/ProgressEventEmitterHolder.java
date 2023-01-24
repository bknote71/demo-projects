package com.example.onlineJudge.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
public class ProgressEventEmitterHolder {

    private static final ThreadLocal<SseEmitter> emitterHolder = new ThreadLocal<>();

    public static void clearHolder() {
        emitterHolder.remove();
    }

    public static SseEmitter getEmitter() {
        SseEmitter emitter = emitterHolder.get();

        if (emitter == null) {
            emitter = createEmitter();
            emitterHolder.set(emitter);
        }

        return emitter;
    }

    public static void setEmitter(SseEmitter emitter) {
        log.info("set emitter");

        emitterHolder.set(emitter);
    }

    public static SseEmitter createEmitter() {
        final SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.onCompletion(() -> {
            System.out.println("emitter complete");

            // remove 가능?
            clearHolder();
        });
        sseEmitter.onError(e -> {
            System.out.println("emitter error");
            clearHolder();
        });

        sseEmitter.onTimeout(() -> {
            System.out.println("emitter error");
            clearHolder();
        });

        return sseEmitter;
    }
}
