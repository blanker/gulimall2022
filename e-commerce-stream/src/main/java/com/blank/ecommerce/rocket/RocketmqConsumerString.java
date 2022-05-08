package com.blank.ecommerce.rocket;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "topic",
        consumerGroup = "string-group"
)
public class RocketmqConsumerString implements RocketMQListener<String>{
    @Override
    public void onMessage(String message) {
        final StreamMessage objMessage = JSON.parseObject(message, StreamMessage.class);
        log.info("RocketmqConsumerString consume: [{}}", JSON.toJSONString(objMessage));
    }
}
