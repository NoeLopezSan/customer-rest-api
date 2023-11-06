package dev.noelopez.restdemo1.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingExecutionTimeAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggingExecutionTimeAspect.class);
    @Pointcut("execution(public * dev.noelopez.restdemo1.controller.*.*(..))")
    public void publicMethodsInController() {}

    @Pointcut("execution(public * dev.noelopez.restdemo1.repo.*.*(..))")
    public void publicMethodsInRepo() {}

    @Pointcut("execution(public * dev.noelopez.restdemo1.service.*.*(..))")
    public void publicMethodsInService() {}

    //@Around("publicMethodsInController() || publicMethodsInRepo()")
    @Around("publicMethodsInController() || publicMethodsInService()")
    public Object logExecutionTime(ProceedingJoinPoint jointPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = jointPoint.proceed();

        long elapseTime = System.currentTimeMillis() - startTime;
        logger.info("Method [{}] with params {} executed in {} ms", jointPoint.getSignature(), jointPoint.getArgs(), elapseTime);
        return result;
    }
}
