package com.blank.ecommerce.fallback_handler;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
public class CommonFallbackHandler {
    public static CommonResponse<JwtToken> getTokenFromAuthorityServiceFallback(
            UsernameAndPassword usernameAndPassword
    ){
        log.error("get token from authority service fallback: [{}]", JSON.toJSONString(usernameAndPassword));

        return new CommonResponse<>(1, "",
                new JwtToken("no token"));
    }


    public static CommonResponse<JwtToken> ignoreExceptionFallback(
            Integer code
    ){
        log.error("ignore exception fallback: [{}]", code);

        return new CommonResponse<>(1, "",
                new JwtToken("no token"));
    }
}
