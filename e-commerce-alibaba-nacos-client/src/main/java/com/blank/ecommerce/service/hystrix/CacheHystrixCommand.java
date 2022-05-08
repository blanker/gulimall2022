package com.blank.ecommerce.service.hystrix;

import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;

@Slf4j
public class CacheHystrixCommand extends HystrixCommand<List<ServiceInstance>> {
    private final NacosClientService nacosClientService;
    private final String serviceId;

    private final static HystrixCommandKey CACHED_KEY
            = HystrixCommandKey.Factory.asKey("CacheHystrixCommand");

    public CacheHystrixCommand(NacosClientService nacosClientService, String serviceId) {
        super(Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("nacosClientService")
                ).andCommandKey(CACHED_KEY).andThreadPoolKey(
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

    public static void flushRequestCache(String serviceId) {
        HystrixRequestCache.getInstance(CACHED_KEY,
                HystrixConcurrencyStrategyDefault.getInstance())
                .clear(serviceId);
        log.info("flush request cache: [{}, {}]", serviceId, Thread.currentThread().getName());
    }

    @Override
    protected List<ServiceInstance> run() throws Exception {
        log.info("Nacos client get service instance : [{}, {}]", serviceId, Thread.currentThread().getName());
        return nacosClientService.getServiceInstance(serviceId);
    }

    @Override
    protected String getCacheKey() {
        return serviceId;
    }

    @Override
    protected List<ServiceInstance> getFallback() {
        return Collections.emptyList();
    }
}
