package com.blank.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.service.NacosClientService;
import com.blank.ecommerce.service.hystrix.*;
import com.blank.ecommerce.service.hystrix.request_merge.NacosClientCollapseCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private UseHystrixCommandAnnotation useHystrixCommandAnnotation;
    @Autowired
    private NacosClientService nacosClientService;

    @GetMapping("/hystrix-command-annotation")
    public List<ServiceInstance> getNacosClientInfoUseAnnotation(
            @RequestParam("serviceId") String serviceId
    ) {
        log.info("request nacos client info use annotation: [{}, {}]", serviceId, Thread.currentThread().getName());
        return useHystrixCommandAnnotation.getNacosClientInfo(serviceId);
    }

    @GetMapping("/simple-hystrix-command")
    public List<ServiceInstance> getNacosClientInfoByServiceId(
            @RequestParam("serviceId") String serviceId
    ) throws ExecutionException, InterruptedException {
        log.info("request nacos client info use annotation: [{}, {}]", serviceId, Thread.currentThread().getName());
        final List<ServiceInstance> instances01 = new NacosClientHystrixCommand(nacosClientService, serviceId).execute();
        log.info("use execute to get service instance: [{}, {}]", JSON.toJSONString(instances01), Thread.currentThread().getName());

        Future<List<ServiceInstance>> future = new NacosClientHystrixCommand(
                nacosClientService, serviceId).queue();
        final List<ServiceInstance> instance02 = future.get();
        log.info("use queue to get service instance: [{}, {}]",
                JSON.toJSONString(instance02), Thread.currentThread().getName());

        final Observable<List<ServiceInstance>> observe = new NacosClientHystrixCommand(
                nacosClientService, serviceId).observe();
        final List<ServiceInstance> instances03 = observe.toBlocking().single();
        log.info("use observe to get service instance: [{}, {}]",
                JSON.toJSONString(instances03), Thread.currentThread().getName());

        final Observable<List<ServiceInstance>> toObservable = new NacosClientHystrixCommand(
                nacosClientService, serviceId).toObservable();
        final List<ServiceInstance> instances04 = toObservable.toBlocking().single();
        log.info("use toObservable to get service instance: [{}, {}]",
                JSON.toJSONString(instances04), Thread.currentThread().getName());

        return instance02;

    }

    @GetMapping("/hystrix-observable-command")
    public List<ServiceInstance> getNacosClientInfoByServiceIdObservable(
            @RequestParam("serviceId") String serviceId
    ) throws ExecutionException, InterruptedException {
        final List<String> serviceIds = Arrays.asList(serviceId, serviceId, serviceId);
        final NacosClientHystrixObservableCommand observableCommand = new NacosClientHystrixObservableCommand(nacosClientService, serviceIds);
        log.info("request nacos client info use observable: [{}, {}]", serviceId, Thread.currentThread().getName());
        final Observable<List<ServiceInstance>> observe = observableCommand.observe();

        List<List<ServiceInstance>> result = new ArrayList<>();

        observe.subscribe(new Observer<List<ServiceInstance>>() {
            @Override
            public void onCompleted() {
                log.info("task completed, {}", Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<ServiceInstance> serviceInstances) {
                log.info("onNext to get service instance: [{}, {}]", JSON.toJSONString(serviceInstances), Thread.currentThread().getName());
                result.add(serviceInstances);
            }
        });

        log.info("result: [{}, {}]", Thread.currentThread().getName(),
                JSON.toJSONString(result));
        return result.get(0);

    }

    @GetMapping("/cache-hystrix-command")
    public void cacheHystrixCommand(@RequestParam("serviceId") String serviceId) {
        final CacheHystrixCommand command1 = new CacheHystrixCommand(nacosClientService, serviceId);
        final CacheHystrixCommand command2 = new CacheHystrixCommand(nacosClientService, serviceId);

        final List<ServiceInstance> result01 = command1.execute();
        final List<ServiceInstance> result02 = command2.execute();

        log.info("cache result: [{}, {}]",
                JSON.toJSONString(result01), JSON.toJSONString(result02));

        CacheHystrixCommand.flushRequestCache(serviceId);

        final CacheHystrixCommand command3 = new CacheHystrixCommand(nacosClientService, serviceId);
        final CacheHystrixCommand command4 = new CacheHystrixCommand(nacosClientService, serviceId);

        final List<ServiceInstance> result03 = command3.execute();
        final List<ServiceInstance> result04 = command4.execute();

        log.info("cache result: [{}, {}]",
                JSON.toJSONString(result03), JSON.toJSONString(result04));
    }

    @Autowired
    private CacheHystrixCommandAnnotation cacheHystrixCommandAnnotation;

    @GetMapping("/cache-hystrix-command-annotation")
    public List<ServiceInstance> cacheHystrixCommandAnnotation(
            @RequestParam("serviceId") String serviceId) {
        cacheHystrixCommandAnnotation.getCache01(serviceId);
        cacheHystrixCommandAnnotation.getCache01(serviceId);

        cacheHystrixCommandAnnotation.cacheRemove01(serviceId);

        cacheHystrixCommandAnnotation.getCache01(serviceId);
        final List<ServiceInstance> result = cacheHystrixCommandAnnotation.getCache01(serviceId);

        return result;
    }

    @GetMapping("/request-merge")
    public void requestMerge(@RequestParam String serviceId) throws ExecutionException, InterruptedException {
        NacosClientCollapseCommand command01 = new NacosClientCollapseCommand(nacosClientService, serviceId+"01");
        NacosClientCollapseCommand command02 = new NacosClientCollapseCommand(nacosClientService, serviceId+"02");
        NacosClientCollapseCommand command03 = new NacosClientCollapseCommand(nacosClientService, serviceId+"03");

        Future<List<ServiceInstance>> future01 = command01.queue();
        Future<List<ServiceInstance>> future02 = command02.queue();
        Future<List<ServiceInstance>> future03 = command03.queue();

        final List<ServiceInstance> serviceInstances = future01.get();
        future02.get();
        future03.get();
        log.info("result 01: [{}, {}]", JSON.toJSONString(serviceInstances), Thread.currentThread().getName());

        TimeUnit.SECONDS.sleep(2);

        NacosClientCollapseCommand command04 = new NacosClientCollapseCommand(nacosClientService, serviceId);

        Future<List<ServiceInstance>> future04 = command04.queue();
        log.info("result 04: [{}, {}]", JSON.toJSONString(future04.get()), Thread.currentThread().getName());

    }

    @GetMapping("/request-merge-annotation")
    public void requestMergeAnnotation(@RequestParam String serviceId) throws ExecutionException, InterruptedException {
        final Future<List<ServiceInstance>> f01 = nacosClientService.findNacosClientInfo(serviceId + "01");
        final Future<List<ServiceInstance>> f02 = nacosClientService.findNacosClientInfo(serviceId + "02");
        final Future<List<ServiceInstance>> f03 = nacosClientService.findNacosClientInfo(serviceId + "03");

        final List<ServiceInstance> serviceInstances = f01.get();
        f02.get();
        f03.get();
        log.info("result 01: [{}, {}]", JSON.toJSONString(serviceInstances), Thread.currentThread().getName());

        TimeUnit.SECONDS.sleep(2);

        Future<List<ServiceInstance>> future04 = nacosClientService.findNacosClientInfo(serviceId );
        log.info("result 04: [{}, {}]", JSON.toJSONString(future04.get()), Thread.currentThread().getName());

    }
}