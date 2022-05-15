package com.blank.ecommerce.conf;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

@Slf4j
public class RestTemplateExceptionUtil {
    public static SentinelClientHttpResponse handleBlock(HttpRequest request,
                                                         byte[] body,
                                                         ClientHttpRequestExecution execution,
                                                         BlockException ex)
    {
        log.error("Handle RestTemplate Block Exception: [{}, {}", request.getURI().getPath(),
                JSON.toJSONString(ex.getRule()));
        return new SentinelClientHttpResponse(
                JSON.toJSONString(new CommonResponse<>(1, "", new JwtToken("token")))
        );
    }

    public static SentinelClientHttpResponse handleFallback(HttpRequest request,
                                                         byte[] body,
                                                         ClientHttpRequestExecution execution,
                                                         BlockException ex)
    {
        log.error("Handle RestTemplate Fallback Exception: [{}, {}", request.getURI().getPath(),
                JSON.toJSONString(ex.getRule()));
        return new SentinelClientHttpResponse(
                JSON.toJSONString(new CommonResponse<>(1, "", new JwtToken("token")))
        );
    }
}
