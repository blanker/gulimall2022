package com.blank.ecommerce.stream.custom;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@EnableBinding(CustomSource.class)
public class CustomSendService {
    @Autowired
    private CustomSource source;

    public void sendMessage(StreamMessage streamMessage) {
        String message = JSON.toJSONString(streamMessage);
        log.info("in sendMessage: [{}]", message);

        source.customOutput().send(MessageBuilder.withPayload(message).build());
    }
}
