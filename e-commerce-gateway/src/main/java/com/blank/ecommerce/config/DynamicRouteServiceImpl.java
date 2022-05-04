package com.blank.ecommerce.config;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {
    private RouteDefinitionWriter routeDefinitionWriter;
    private RouteDefinitionLocator routeDefinitionLocator;
    private ApplicationEventPublisher publisher;

    DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter, RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public void setApplicationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public String addRouteDefinition(RouteDefinition definition) {
        log.info("add route definition: [{}]", JSON.toJSONString(definition));
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    public String updateRouteDefinitions(List<RouteDefinition> list) {
        final List<RouteDefinition> exists =
                routeDefinitionLocator.getRouteDefinitions()
                        .buffer().blockFirst();
        if (!Collections.isEmpty(exists)) {
            exists.forEach(def -> {
                deleteById(def.getId());
            });
        }

        list.forEach(this::updateById);

        return "success";
    }

    private String deleteById(String id) {
        try {
            log.info("delete gateway route by id: [{}]", id);
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";

        } catch(Exception ex) {
            log.info("delete gateway error, id: [{}], message: [{}]", id, ex.getMessage(), ex);
            return "delete failed";
        }
    }

    private String updateById(RouteDefinition definition) {
        try {
            log.info("update gateway route by id: [{}], [{}]", definition.getId(), JSON.toJSONString(definition));
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
            addRouteDefinition(definition);
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return "update success";

        } catch(Exception ex) {
            log.info("update gateway error, id: [{}], message: [{}]", definition.getId(), ex.getMessage(), ex);
            return "update failed";
        }
    }
}
