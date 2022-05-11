package com.blank.ecommerce.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LogisticsSink {
    String INPUT = "logisticsInput";

    @Input(INPUT)
    SubscribableChannel logisticsInput();

}
