package com.blank.ecommerce.service.communication.hystrix;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.service.communication.AuthorityFeignClient;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorityFeignClientFallbackFactory implements FallbackFactory<AuthorityFeignClient> {

    @Override
    public AuthorityFeignClient create(Throwable cause) {
        log.warn("get token by feign request error (Hystrix FallbackFactory): [{}]",
                cause.getMessage(), cause);
        return usernameAndPassword -> {
            final CommonResponse<JwtToken> result = new CommonResponse<>();
            result.setData(new JwtToken("null"));
            return result;
        };
    }
}
