package com.blank.ecommerce.service.hystrix;

import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
public class NacosClientHystrixCommand extends HystrixCommand<List<ServiceInstance>> {

    private final NacosClientService nacosClientService;
    private final String serviceId;

    public NacosClientHystrixCommand(NacosClientService nacosClientService, String serviceId) {
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("nacosClientService")
        ).andCommandKey(
                HystrixCommandKey.Factory.asKey("nacosClientService")
        ).andThreadPoolKey(
                HystrixThreadPoolKey.Factory.asKey("nacosClientPool")
                ).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        .withFallbackEnabled(true)
                        .withCircuitBreakerEnabled(true)
                )
        );

        Setter semaphore = Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("nacosClientService")
        ).andCommandKey(
                HystrixCommandKey.Factory.asKey("nacosClientService")
        ).andThreadPoolKey(
                HystrixThreadPoolKey.Factory.asKey("nacosClientPool")
        ).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withFallbackEnabled(true)
                        .withCircuitBreakerEnabled(true)
        );

        this.nacosClientService = nacosClientService;
        this.serviceId = serviceId;
    }

    @Override
    public List<ServiceInstance> run() throws Exception {
        log.info("Nacos client get service instance : [{}, {}]", serviceId, Thread.currentThread().getName());
        return nacosClientService.getServiceInstance(serviceId);
    }

    @Override
    public List<ServiceInstance> getFallback() {
        log.warn("Nacos client get service instance error : [{}, {}]", serviceId, Thread.currentThread().getName());
        return Collections.emptyList();
    }
}
