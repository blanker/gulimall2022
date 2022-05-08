package com.blank.ecommerce.controller;

import com.blank.ecommerce.kafka.KafkaConsumer;
import com.blank.ecommerce.kafka.KafkaProducer;
import com.blank.ecommerce.vo.StreamMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired(required = false)
    private KafkaProducer kafkaProducer;
    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/send-message")
    public void sendMessage(
            @RequestParam(required=false) String key,
            @RequestParam String topic) throws Exception {
        StreamMessage message = new StreamMessage(1, "ecommerce-stream");
        kafkaProducer.sendMessage(key, mapper.writeValueAsString(message), topic);
    }
}
