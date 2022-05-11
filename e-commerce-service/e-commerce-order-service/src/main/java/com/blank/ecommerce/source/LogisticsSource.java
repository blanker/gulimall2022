package com.blank.ecommerce.source;

import org.checkerframework.checker.signature.qual.ArrayWithoutPackage;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface LogisticsSource {
    String OUTPUT = "logisticsOutput";
    @Output(OUTPUT)
    MessageChannel logisticsOutput();
}
