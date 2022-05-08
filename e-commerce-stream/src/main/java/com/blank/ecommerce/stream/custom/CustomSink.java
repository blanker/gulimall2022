package com.blank.ecommerce.stream.custom;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomSink {
    String INPUT = "custom-input";
    @Input (CustomSink.INPUT)
    SubscribableChannel customInput();
}
