package com.group.AccountZen.LoggerConfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(MethodLoggingAspect.class);

    @Before("execution(* com.group.AccountZen.controller.*.*(..))")
    public void logControllerMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Entering controller method: " + methodName);
    }

    @Before("execution(* com.group.AccountZen.service.*.*(..))")
    public void logServiceMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Entering service method: " + methodName);
    }

    @AfterReturning(pointcut = "execution(* com.group.AccountZen.controller.*.*(..))", returning = "result")
    public void logControllerMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Exiting controller method: " + methodName);
    }

    @AfterReturning(pointcut = "execution(* com.group.AccountZen.service.*.*(..))", returning = "result")
    public void logServiceMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Exiting service method: " + methodName);
    }
}