package com.definex.practicum.finalcase.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    // Pointcut for Controller methods.
    @Pointcut("execution(* com.definex.practicum.finalcase.controller.*.*.*(..))")
    public void controllerMethodsPointcut() {
    }


    @Before("controllerMethodsPointcut()")
    public void beforeControllerLogging(JoinPoint joinPoint) {
        LOGGER.info("Received request : " + joinPoint.getSignature().getName() + ", with args: "
                + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "controllerMethodsPointcut()", returning = "result")
    public void afterReturningControllerLogging(JoinPoint joinPoint, Object result) {
        LOGGER.info("Request : " + joinPoint.getSignature().getName() + ", Returned: "
                + result);
    }

    @AfterThrowing(pointcut = "controllerMethodsPointcut()", throwing = "ex")
    public void afterThrowingControllerLogging(JoinPoint joinPoint, Throwable ex) {
        LOGGER.error("Exception thrown in controller method : " + joinPoint.getSignature().getName() + ", with args: "
                + Arrays.toString(joinPoint.getArgs()) + ", Exception: " + ex.getMessage());
    }

}
