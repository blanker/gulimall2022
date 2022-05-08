package com.blank.ecommerce.stream.custom;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(CustomSink.class)
public class CustomReceiveService {

    @StreamListener(CustomSink.INPUT)
    public void receiveMessage(Object payload) {
        final StreamMessage message = JSON.parseObject(payload.toString(), StreamMessage.class);
        log.info("in receiveMessage , consume message: [{}]", JSON.toJSONString(message));
    }
}
