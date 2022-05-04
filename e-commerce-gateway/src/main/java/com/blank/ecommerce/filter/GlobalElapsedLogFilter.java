package com.blank.ecommerce.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalElapsedLogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
        StopWatch sw = new StopWatch();
        sw.start();
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    sw.stop();
                    log.info("[{}] elapsed [{}ms]", uri, sw.getTotalTimeMillis());
                })
        );
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
