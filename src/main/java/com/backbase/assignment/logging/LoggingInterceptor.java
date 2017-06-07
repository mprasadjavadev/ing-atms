package com.backbase.assignment.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by Ahmad on 6/4/2017.
 */
@Aspect
@Component
public class LoggingInterceptor {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);

    @Before("execution(* com.backbase.assignment..*.*())")
    public void logBeforeMethod(JoinPoint joinPoint) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("UserDto :");
        logMessage.append("Anonymous user");
        logMessage.append(" ,Enter :");
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());

        LOG.info(logMessage.toString());

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            StringBuilder logParameters = new StringBuilder();
            logParameters.append("Method paramaters ( ");
            for (Object arg : args) {
                logParameters.append(arg == null ? "null" : arg).append(",");
            }
            if (args.length > 0) {
                logParameters.deleteCharAt(logParameters.length() - 1);
            }
            logParameters.append(" ).");

            LOG.debug(logParameters.toString());
        }

    }

    @AfterReturning("execution(* com.backbase.assignment..*.*())")
    public void logAfterMethodSuccess(JoinPoint joinPoint) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Finish :");
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("successfully.");

        LOG.info(logMessage.toString());

    }

    @AfterThrowing(pointcut = "execution(* com.backbase.assignment..*.*())", throwing = "ex")
    public void logAfterMethodFaill(JoinPoint joinPoint, Throwable ex) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Finish :");
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append(", with error: ");
        logMessage.append(ex);

        LOG.error(logMessage.toString());
    }

}
