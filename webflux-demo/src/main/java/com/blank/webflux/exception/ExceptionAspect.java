package com.blank.webflux.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ExceptionAspect {
    @AfterThrowing(pointcut = "within(com.blank.webflux.controller.*)", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) throws Exception {
        final Class declaringType = joinPoint.getSignature().getDeclaringType();
        final String clazz = declaringType.getCanonicalName();
        final String name = joinPoint.getSignature().getName();
        if (ex instanceof BusiException) {
            log.warn("class: {}, name: {}", clazz, name, ex);
        } else if (ex instanceof SystemException) {
            log.error("class: {}, name: {}", clazz, name, ex);
        }
        throw ex;
    }
}
