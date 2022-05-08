package com.blank.ecommerce.rocket;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "topic",
        consumerGroup = "message-ext-group"
)
public class RocketmqConsumerExtMessage implements RocketMQListener<MessageExt>{


    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        final StreamMessage objMessage = JSON.parseObject(message, StreamMessage.class);
        log.info("RocketmqConsumerExtMessage consume: [{}, {}]",
                JSON.toJSONString(objMessage), messageExt.getKeys());
        log.info("RocketmqConsumerExtMessage ext message: [{}]", JSON.toJSONString(messageExt));
    }
}
