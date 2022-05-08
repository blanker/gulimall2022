package com.blank.ecommerce.controller;

import com.blank.ecommerce.stream.DefaultSendService;
import com.blank.ecommerce.stream.custom.CustomSendService;
import com.blank.ecommerce.vo.StreamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stream")
public class StreamMessageController {

    @Autowired
    private DefaultSendService defaultSendService;
    @Autowired
    private CustomSendService customSendService;

    @GetMapping("/default")
    public void defaultSend() {
        defaultSendService.sendMessage(StreamMessage.defaultMessage());
    }
    @GetMapping("/custom")
    public void customSend() {
        customSendService.sendMessage(StreamMessage.defaultMessage());
    }
}
