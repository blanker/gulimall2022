package com.blank.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.rocket.RocketmqProducer;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rocketmq")
public class RocketmqController {
    private static final StreamMessage message = new StreamMessage(1, "message body");
    @Autowired
    private RocketmqProducer producer;

    @RequestMapping("/send-with-value")
    public void sendWithValue(){
        producer.sendMessageWithValue(JSON.toJSONString(message));
    }

    @RequestMapping("/send-with-key")
    public void sendWithKey() {
        producer.sendMessageWithKey("key", JSON.toJSONString(message));
    }

    @RequestMapping("/send-with-tag")
    public void sendWithTag() {
        producer.sendMessageWithTag("tag", JSON.toJSONString(message));
    }

    @RequestMapping("/send-with-all")
    public void sendWithAll() {
        producer.sendMessageWithAll("key", "tag", JSON.toJSONString(message));
    }
}
