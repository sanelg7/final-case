package com.definex.practicum.finalcase.aop;

import com.definex.practicum.finalcase.exception.EntityNotFoundException;
import com.definex.practicum.finalcase.exception.UserUpdateException;
import com.definex.practicum.finalcase.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class UserLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoggingAspect.class);

    // Pointcut for Controller methods.
    @Pointcut("execution(* com.definex.practicum.finalcase.controller.UserController.*(..))")
    public void userControllerMethods(){}
/*
    // Pointcut for Service methods.
    @Pointcut("execution(* com.definex.practicum.finalcase.service.UserService.*(..))")
    public void userServiceMethods(){}
*/

    @Before("userControllerMethods()")
    public void beforeControllerLogging(JoinPoint joinPoint){
        LOGGER.info("Received request : " + joinPoint.getSignature().getName() + ", with args: "
                + Arrays.toString(joinPoint.getArgs()));
    }
    @AfterReturning(value = "userControllerMethods()", returning = "result")
    public void afterReturningControllerLogging(JoinPoint joinPoint, Object result){
        LOGGER.info("Request : " + joinPoint.getSignature().getName() +  ", Returned: "
                + result);
    }

    @AfterThrowing(value = "execution(* com.definex.practicum.finalcase.service.UserServiceImpl.update(..))", throwing = "ex")
    public void afterThrowingServiceLogging(JoinPoint joinPoint, UserUpdateException ex) {
        User user = (User) joinPoint.getArgs()[0];
        LOGGER.error("Error while updating user: " + user + " ,  " + ex.getMessage());
    }

    // Handles the logging of both exceptions.
    @AfterThrowing(value = "execution(* com.definex.practicum.finalcase.controller.UserController.*(..))", throwing = "ex")
    public void afterThrowingControllerLogging(JoinPoint joinPoint, EntityNotFoundException ex){
        LOGGER.error("User required by method not found." + "Method: " + joinPoint.getSignature().getName() + " with args: " + joinPoint.getArgs());

    }

 /*   @Before("userServiceMethods()")
    public void userServiceLogging(JoinPoint joinPoint){
        LOGGER.info("Service - Incoming data: " + joinPoint.getSignature().getName()
                + Arrays.toString(joinPoint.getArgs()));
    }*/
  /*  @AfterReturning(value = "execution(* com.definex.practicum.finalcase.controller.UserController.deleteUser(..))", returning = "result")
    public void logDeleteUser(JoinPoint joinPoint, Object result) {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("User deleted: " + id);
    }

    @AfterReturning(value = "execution(* com.definex.practicum.finalcase.controller.UserController.updateUser(..))", returning = "result")
    public void logUpdateUser(JoinPoint joinPoint, Object result) {
        User user = (User) joinPoint.getArgs()[0];
        logger.info("User updated: " + user.toString());
    }*/

}
