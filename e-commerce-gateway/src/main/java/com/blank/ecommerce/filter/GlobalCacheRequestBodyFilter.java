package com.blank.ecommerce.filter;

import com.blank.ecommerce.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        boolean isLoginOrRegister = exchange.getRequest().getURI().getPath().contains(GatewayConstant.LOGIN_URI)
                || exchange.getRequest().getURI().getPath().contains(GatewayConstant.REGISTER_URI);

        if (null == exchange.getRequest().getHeaders().getContentType()
            || !isLoginOrRegister) {
            return chain.filter(exchange);
        }

        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    // 确保数据buffer不被释放
                    DataBufferUtils.retain(dataBuffer);
                    // 得到副本
                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                        return Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount()));
                    });

                    // 包装新的request
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
                            exchange.getRequest()
                    ){
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };

                    // 使用包装后的request
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });

    }
}
