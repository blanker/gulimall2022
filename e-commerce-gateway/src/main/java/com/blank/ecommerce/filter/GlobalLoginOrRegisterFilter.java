package com.blank.ecommerce.filter;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.constant.GatewayConstant;
import com.blank.ecommerce.util.TokenParseUtil;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.LoginUserInfo;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();

        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI) ||
                request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
            return chain.filter(exchange);
        }

        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT);
            response.getHeaders().add(
                    CommonConstant.JWT_USER_INFO_KEY,
                    null == token? "null": token
            );
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }
        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT);
            response.getHeaders().add(
                    CommonConstant.JWT_USER_INFO_KEY,
                    null == token? "null": token
            );
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }
        HttpHeaders headers = request.getHeaders();
        final String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {
            log.error("parse user info from token error. [{}]", ex.getMessage(), ex);
        }

        if ( null == loginUserInfo ){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }

    private String getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat){
        final ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("Nacos client info : [{}, {}, {}]", serviceInstance.getInstanceId(), serviceInstance.getServiceId(),
                JSON.toJSONString(serviceInstance.getMetadata()));
        final String requestUrl = String.format(uriFormat, serviceInstance.getHost(), serviceInstance.getPort());
        UsernameAndPassword usernameAndPassword = JSON.parseObject(parseBodyFromRequest(request), UsernameAndPassword.class);
        log.info("login or request request: [{}, {}", requestUrl, JSON.toJSONString(usernameAndPassword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JwtToken jwtToken = restTemplate.postForObject(requestUrl,
                new HttpEntity(JSON.toJSONString(usernameAndPassword), headers),
                JwtToken.class
        );
        if (null != jwtToken) {
            return jwtToken.getToken();
        }
        return null;
    }

    private String parseBodyFromRequest(ServerHttpRequest request){
        Flux<DataBuffer> body = request.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();

        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }
}
