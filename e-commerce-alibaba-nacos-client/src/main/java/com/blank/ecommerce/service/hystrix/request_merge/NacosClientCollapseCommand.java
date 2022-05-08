package com.blank.ecommerce.service.hystrix.request_merge;

import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NacosClientCollapseCommand extends HystrixCollapser<List<List<ServiceInstance>>, List<ServiceInstance>, String> {
    private NacosClientService nacosClientService;
    private String serviceId;

    public NacosClientCollapseCommand(
            NacosClientService nacosClientService,
            String serviceId
    ){
        super(Setter.withCollapserKey(
                HystrixCollapserKey.Factory.asKey("NacosClientCollapseCommand"))
                .andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(300)
                )
        );

        this.nacosClientService = nacosClientService;
        this.serviceId = serviceId;
    }

    @Override
    public String getRequestArgument() {
        return serviceId;
    }

    @Override
    protected HystrixCommand<List<List<ServiceInstance>>> createCommand(
            Collection<CollapsedRequest<List<ServiceInstance>, String>> collapsedRequests) {
        List<String> serviceIds = new ArrayList<>(collapsedRequests.size());
        serviceIds.addAll(collapsedRequests.stream()
                .map(CollapsedRequest::getArgument)
                .collect(Collectors.toList())
        );

        return new NacosClientBatchCommand(nacosClientService, serviceIds);
    }

    @Override
    protected void mapResponseToRequests(
            List<List<ServiceInstance>> batchResponse,
            Collection<CollapsedRequest<List<ServiceInstance>, String>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<List<ServiceInstance>, String> collapsedRequest : collapsedRequests) {
            final List<ServiceInstance> serviceInstances = batchResponse.get(count++);
            collapsedRequest.setResponse(serviceInstances);
        }
    }
}
