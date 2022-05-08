package com.blank.ecommerce.service.hystrix;

import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UseHystrixCommandAnnotation {
    @Autowired
    private NacosClientService nacosClientService;

    @HystrixCommand(
            groupKey = "NacosClientService",
            commandKey = "NacosClientService",
            threadPoolKey = "NacosClientService",
            fallbackMethod = "getNacosClientInfoFallback",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1500"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50")
            },
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="30"),
                    @HystrixProperty(name="maxQueueSize", value="101"),
                    @HystrixProperty(name="keepAliveTimeMinutes", value="2"),
                    @HystrixProperty(name="queueSizeRejectionThreshold", value="15"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="12"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="1440"),
            }
    )
    public List<ServiceInstance> getNacosClientInfo(String serviceId) {
        log.info("use hystrix command annotation to get nacos service instance: [{}, {}] ",
                serviceId, Thread.currentThread().getName());
        return nacosClientService.getServiceInstance(serviceId);
    }

    public List<ServiceInstance> getNacosClientInfoFallback(String serviceId) {
        log.info("cannot get nacos service instance: [{}, {}] ",
                serviceId, Thread.currentThread().getName());
        return Collections.emptyList();
    }
}
