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
        consumerGroup = "object-group",
        selectorExpression = "tag"
)
public class RocketmqConsumerObject implements RocketMQListener<StreamMessage>{
    @Override
    public void onMessage(StreamMessage message) {
        log.info("RocketmqConsumerString consume: [{}}", JSON.toJSONString(message));
    }
}
