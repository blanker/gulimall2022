package com.blank.ecommerce.kafka;

import com.blank.ecommerce.vo.StreamMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
//@Component
public class KafkaConsumer {
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = {"topic"}, groupId = "group-consumer-01")
    public void listener01(ConsumerRecord<String, String> record) throws Exception{
        String key = record.key();
        final String value = record.value();

        StreamMessage message = objectMapper.readValue(value, StreamMessage.class);
        log.info("in listener01 consume kafka message: [{}, {}]", key, objectMapper.writeValueAsString(message));
    }

    @KafkaListener(topics = {"topic"}, groupId = "group-consumer-02")
    public void listener02(ConsumerRecord<?, ?> record) throws Exception{
        Optional<?> _kafkaMessage = Optional.of(record.value());
        if (_kafkaMessage.isPresent()) {
            Object message = _kafkaMessage.get();
            StreamMessage streamMessage = objectMapper.readValue(message.toString(), StreamMessage.class);
            log.info("in listener02 consume kafka message: [{}]", objectMapper.writeValueAsString(message));
        }
    }

}
