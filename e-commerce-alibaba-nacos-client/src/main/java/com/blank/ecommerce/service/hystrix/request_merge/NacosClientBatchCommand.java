package com.blank.ecommerce.service.hystrix.request_merge;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import sun.security.jca.ServiceId;

import java.util.Collections;
import java.util.List;

@Slf4j
public class NacosClientBatchCommand extends HystrixCommand<List<List<ServiceInstance>>> {
    private NacosClientService nacosClientService;
    private List<String> serviceIds;

    public NacosClientBatchCommand(NacosClientService nacosClientService, List<String> serviceIds){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("NacosClientBatchCommand")));

        this.nacosClientService = nacosClientService;
        this.serviceIds = serviceIds;
    }

    @Override
    protected List<List<ServiceInstance>> run() throws Exception {
        log.info("batch get serviceInstance: [{}, {}]",
                JSON.toJSONString(serviceIds), Thread.currentThread().getName());
        return nacosClientService.getServiceInstance(serviceIds);
    }

    @Override
    protected List<List<ServiceInstance>> getFallback() {
        log.warn("batch get service client failure");
        return Collections.emptyList();
    }
}
