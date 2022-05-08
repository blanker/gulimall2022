package com.blank.ecommerce.stream.custom;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomSource {
    String OUTPUT = "custom-output";
    @Output(CustomSource.OUTPUT)
    MessageChannel customOutput();


}
