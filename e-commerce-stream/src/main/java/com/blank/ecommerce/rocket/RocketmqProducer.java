package com.blank.ecommerce.rocket;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.bouncycastle.its.asn1.IValue;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RocketmqProducer {
    private static final String TOPIC = "topic";
    private RocketMQTemplate rocketMQTemplate;

    public RocketmqProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
    public void sendMessageWithValue(String value){
        final SendResult sendResult = rocketMQTemplate.syncSend(TOPIC, value);
        log.info("sendMessageWithValue success: [{}]", JSON.toJSONString(sendResult));

        final SendResult sendOrderly = rocketMQTemplate.syncSendOrderly(TOPIC, value, "key");
        log.info("sendMessageWithValue orderly success: [{}]", JSON.toJSONString(sendOrderly));
    }

    public void sendMessageWithKey(String key, String value) {
        final Message<String> message = MessageBuilder.withPayload(value)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();

        rocketMQTemplate.asyncSend(TOPIC, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sendMessageWithKey success: [{}]", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("sendMessageWithKey failure: [{}]", JSON.toJSONString(throwable.getMessage()),throwable);
            }
        });
    }

    public void sendMessageWithTag(String tag, String value) {
        StreamMessage message = JSON.parseObject(value, StreamMessage.class);
        final SendResult sendResult = rocketMQTemplate.syncSend(
                String.format("%s:%s", TOPIC, tag),
                message);
        log.info("sendMessageWithTag success: [{}]", JSON.toJSONString(sendResult));
    }

    public void sendMessageWithAll(String key, String tag, String value) {
        final Message<String> message = MessageBuilder.withPayload(value)
                .setHeader(RocketMQHeaders.KEYS, key)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .build();
        final SendResult sendResult = rocketMQTemplate.syncSend(
                String.format("%s:%s", TOPIC, tag),
                message);

        log.info("sendMessageWithAll success: [{}]", JSON.toJSONString(sendResult));
    }
}
