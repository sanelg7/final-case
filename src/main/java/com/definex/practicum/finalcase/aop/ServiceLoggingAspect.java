package com.definex.practicum.finalcase.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class ServiceLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    // Pointcut for Controller methods.
    @Pointcut("execution(* com.definex.practicum.finalcase.service.impl.*.*(..))")
    public void serviceMethodsPointcut() {
    }

    @Before("serviceMethodsPointcut()")
    public void beforeServiceLogging(JoinPoint joinPoint) {
        LOGGER.info("Calling service method: " + joinPoint.getSignature().getName() + ", with args: "
                + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "serviceMethodsPointcut()", returning = "result")
    public void afterReturningServiceLogging(JoinPoint joinPoint, Object result) {
        LOGGER.info("Service method: " + joinPoint.getSignature().getName() + ", Returned: "
                + result);
    }

    @AfterThrowing(pointcut = "serviceMethodsPointcut()", throwing = "ex")
    public void afterThrowingServiceLogging(JoinPoint joinPoint, Throwable ex) {
        LOGGER.error("Exception thrown in service method : " + joinPoint.getSignature().getName() + ", with args: "
                + Arrays.toString(joinPoint.getArgs()) + ", Exception: " + ex.getMessage());
    }

}
