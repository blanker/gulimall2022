package com.blank.ecommerce.advice;

import com.blank.ecommerce.annotation.IgnoreResponseAdvice;
import com.blank.ecommerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice("com.blank.ecommerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getParameterType().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (returnType.hasMethodAnnotation(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        CommonResponse<Object> result = new CommonResponse<>(0, "");
        if (null == body){
            return result;
        } else if (body instanceof CommonResponse) {
            return body;
        }
        result.setData(body);
        return result;
    }
}
