package com.blank.ecommerce.stream;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(Sink.class)
public class DefaultReceiveService {

    @StreamListener(Sink.INPUT)
    public void receiveMessage(Object payload) {
        final StreamMessage message = JSON.parseObject(payload.toString(), StreamMessage.class);
        log.info("in receiveMessage , consume message: [{}]", JSON.toJSONString(message));
    }
}
