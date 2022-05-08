package com.blank.ecommerce.stream;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@EnableBinding(Source.class)
public class DefaultSendService {
    @Autowired
    private Source source;

    public void sendMessage(StreamMessage streamMessage) {
        String message = JSON.toJSONString(streamMessage);
        log.info("in sendMessage: [{}]", message);

        source.output().send(MessageBuilder.withPayload(message).build());
    }
}
