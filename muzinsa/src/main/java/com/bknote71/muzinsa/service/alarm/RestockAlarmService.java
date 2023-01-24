package com.bknote71.muzinsa.service.alarm;

import com.bknote71.muzinsa.infra.repository.nonedip.RestockSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RestockAlarmService {

    private final RestockSubscriptionRepository restockSubscriptionRepository;
    private final RestockAlarmProducer alarmProducer;

    @Transactional
    @KafkaListener(topics = "restock", groupId = "foo")
    public void consume(String message) {
        System.out.println(String.format("Consumed message : %s", message));

    }

    public void produce(String message) {
        alarmProducer.sendMessage(message);
    }
}
