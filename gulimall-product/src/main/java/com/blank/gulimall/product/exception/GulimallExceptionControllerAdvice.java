package com.blank.gulimall.product.exception;

import com.blank.common.exception.BusiCodeEnum;
import com.blank.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.blank.gulimall.product")
@Slf4j
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException ex){
        log.error("数据校验出现问题: {}, 异常类型, {}", ex.getMessage(), ex.getClass(), ex);
        final BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errorMap.put(err.getField(), err.getDefaultMessage());
        });
        return R.error(BusiCodeEnum.VALID_EXCEPTION.getCode(), BusiCodeEnum.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("出现问题: {}, 异常类型, {}", throwable.getMessage(), throwable.getClass(), throwable);
        return R.error(BusiCodeEnum.UNKNOWN_EXCEPTION.getCode(), BusiCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
