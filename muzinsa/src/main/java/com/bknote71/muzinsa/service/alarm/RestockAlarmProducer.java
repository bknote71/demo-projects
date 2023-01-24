package com.bknote71.muzinsa.service.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestockAlarmProducer {
    private static final String ALARM_TOPIC = "restock";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        System.out.println(String.format("Produce message : %s", message));
        this.kafkaTemplate.send(ALARM_TOPIC, message);
    }
}
