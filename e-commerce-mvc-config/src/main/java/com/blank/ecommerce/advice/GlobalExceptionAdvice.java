package com.blank.ecommerce.advice;

import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value={Exception.class})
    public CommonResponse<String> handlerException(
            HttpServletRequest request, Exception ex
    ){
        CommonResponse<String> response = new CommonResponse<>(-1, "Business Error");
        log.error("service has error: [{}]", ex.getMessage(), ex);
        response.setData(ex.getMessage());
        return response;
    }
}
