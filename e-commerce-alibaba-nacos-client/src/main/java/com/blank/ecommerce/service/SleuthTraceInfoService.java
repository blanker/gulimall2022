package com.blank.ecommerce.service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SleuthTraceInfoService {
    @Autowired
    private Tracer tracer;

    public void logCurrentTraceInfo(){
        log.info("current trace info[traceId: {}, spanId: {}]", tracer.currentSpan().context().traceId()
                , tracer.currentSpan().context().spanId());
    }
}
