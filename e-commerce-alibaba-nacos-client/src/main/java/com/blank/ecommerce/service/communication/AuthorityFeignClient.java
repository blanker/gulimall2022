package com.blank.ecommerce.service.communication;

import com.blank.ecommerce.service.communication.hystrix.AuthorityFeignClientFallback;
import com.blank.ecommerce.service.communication.hystrix.AuthorityFeignClientFallbackFactory;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        contextId = "AuthorityFeignClient",
        value="ecommerce-authority-center",
//        fallback = AuthorityFeignClientFallback.class,
        fallbackFactory = AuthorityFeignClientFallbackFactory.class
)
public interface AuthorityFeignClient {

    @RequestMapping(
            value = "/ecommerce-authority-center/authority/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CommonResponse<JwtToken> token(UsernameAndPassword usernameAndPassword) throws Exception ;
}
