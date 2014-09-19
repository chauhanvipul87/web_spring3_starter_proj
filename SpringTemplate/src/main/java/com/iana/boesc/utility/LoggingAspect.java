package com.iana.boesc.utility;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class LoggingAspect
{
	
    private Logger log = Logger.getLogger(LoggingAspect.class);
     
    @Pointcut("execution(* com.iana..*.*(..))")
    protected void loggingOperation() {}
     
    @Before("loggingOperation()")
    @Order(1)
    public void logJoinPoint(JoinPoint joinPoint)
    {
     StringBuilder sb = new StringBuilder(joinPoint.getSignature().getDeclaringTypeName());
     sb.append(" -->");
     sb.append(joinPoint.getSignature().getName()+"() Method Start");
     log.info(sb.toString());
    }
         
    @AfterReturning(pointcut="loggingOperation()", returning = "result")
    @Order(2)
    public void logAfter(JoinPoint joinPoint, Object result)
    {
     StringBuilder sbLogAfter = new StringBuilder(joinPoint.getSignature().getDeclaringTypeName());
     sbLogAfter.append(" -->");
     sbLogAfter.append(joinPoint.getSignature().getName()+"() Method End");
     log.info(sbLogAfter.toString());
    }
     
    @AfterThrowing(pointcut="execution(* com.iana..*.*(..))", throwing = "e")
    @Order(3)
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e)
    {
     log.error("An exception has been thrown in "+ joinPoint.getSignature().getName() + "()");
        log.error("Cause :"+e.getCause());
        log.error("Cause Message :"+e.getMessage());
        log.error("Extra Information :"+e.toString());
         
        StringBuilder sbLogAfter = new StringBuilder("An exception has been thrown in "+joinPoint.getSignature().getDeclaringTypeName());
     sbLogAfter.append(" -->");
     sbLogAfter.append(joinPoint.getSignature().getName()+"() Method End");
     log.error(sbLogAfter.toString());
    }
     
    @Around("execution(* com.iana..*.*(..))")
    @Order(4)
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable
    {
       log.info("The method " + joinPoint.getSignature().getName()+ "() begins with " + Arrays.toString(joinPoint.getArgs()));
        try
        {
            Object result = joinPoint.proceed();
            log.info("The method " + joinPoint.getSignature().getName()+ "() ends with " + result);
            return result;
        } catch (Throwable e)
        {
            log.error("Illegal argument "+ Arrays.toString(joinPoint.getArgs()) + " in "+ joinPoint.getSignature().getName() + "()");
            throw e;
        }        
    }
     
}