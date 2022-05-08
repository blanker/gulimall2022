package com.blank.ecommerce.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
//@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String key, String value, String topic){
        if (StringUtils.isAnyBlank(value, topic)) {
            throw new IllegalArgumentException("value or topic cannot blank.");
        }
        final ListenableFuture<SendResult<String, String>> future
                = StringUtils.isBlank(key)
                ? kafkaTemplate.send(topic, value)
                : kafkaTemplate.send(topic, key, value);

        // 异步回调
        future.addCallback(
                success -> {
                    assert null != success && null != success.getRecordMetadata();
                    final String _topic = success.getRecordMetadata().topic();
                    final long offset = success.getRecordMetadata().offset();
                    final int partition = success.getRecordMetadata().partition();
                    log.info("send kafka message success: [{}, {}, {}]", _topic, offset, partition);
                }, failure -> {
                    log.error("send kafka message failure: [{}, {}, {}", key, value, topic, failure);
                }
        );

        // 同步获取回调
        try {
            final SendResult<String, String> result = future.get();
            final String _topic = result.getRecordMetadata().topic();
            final long offset = result.getRecordMetadata().offset();
            final int partition = result.getRecordMetadata().partition();
            log.info("send kafka message success: [{}, {}, {}]", _topic, offset, partition);
        } catch (Exception ex){
            log.error("send kafka message failure: [{}, {}, {}", key, value, topic, ex);
        }
    }
}
