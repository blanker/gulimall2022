package com.blank.ecommerce.service.hystrix;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import rx.Observable;
import rx.Subscriber;

import java.util.Collections;
import java.util.List;

@Slf4j
public class NacosClientHystrixObservableCommand extends HystrixObservableCommand<List<ServiceInstance>> {
    private final NacosClientService nacosClientService;
    private final List<String> serviceIds;

    public NacosClientHystrixObservableCommand(
            NacosClientService nacosClientService, List<String> serviceIds
    ){
        super(
                Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("nacosClientService")
                ).andCommandKey(HystrixCommandKey.Factory.asKey("nacosClientService"))
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withFallbackEnabled(true)
                                        .withCircuitBreakerEnabled(true)
                        )
        );

        this.nacosClientService = nacosClientService;
        this.serviceIds = serviceIds;
    }

    @Override
    protected Observable<List<ServiceInstance>> construct() {
        return Observable.create(new Observable.OnSubscribe<List<ServiceInstance>>() {
            @Override
            public void call(Subscriber<? super List<ServiceInstance>> subscriber) {

                try {
                    if (subscriber.isUnsubscribed()) {
                        log.info("subscriber command task: [{}, {}]",
                                JSON.toJSONString(serviceIds),
                                Thread.currentThread().getName());
                        for (String serviceId : serviceIds) {
                            subscriber.onNext(nacosClientService.getServiceInstance(serviceId));
                        }
                        subscriber.onCompleted();
                        log.info("subscriber command completed: [{}, {}]",
                                JSON.toJSONString(serviceIds),
                                Thread.currentThread().getName());
                    }
                } catch ( Exception ex){
                    subscriber.onError(ex);
                }
            }
        });
    }

    @Override
    protected Observable<List<ServiceInstance>> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<List<ServiceInstance>>() {
            @Override
            public void call(Subscriber<? super List<ServiceInstance>> subscriber) {
                try {
                    if (subscriber.isUnsubscribed()) {
                        log.info("subscriber command task fallback: [{}, {}]",
                                JSON.toJSONString(serviceIds),
                                Thread.currentThread().getName());
                        subscriber.onNext(Collections.emptyList());
                        subscriber.onCompleted();
                        log.info("subscriber command fallback completed: [{}, {}]",
                                JSON.toJSONString(serviceIds),
                                Thread.currentThread().getName());
                    }
                } catch ( Exception ex){
                    subscriber.onError(ex);
                }
            }
        });
    }
}
