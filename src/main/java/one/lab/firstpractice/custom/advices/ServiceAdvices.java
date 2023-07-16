package one.lab.firstpractice.custom.advices;

import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import one.lab.firstpractice.custom.annotation.Timed;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class ServiceAdvices {

    public static final String BEFORE_ADVICE = "[BEFORE ADVICE LOGGING] ";
    public static final String AFTER_RETURNING_ADVICE = "[AFTER_RETURNING ADVICE LOGGING] ";
    public static final String AROUND_ADVICE = "[AROUND LOGGING] ";


    @Before("@annotation(loggable)")
    public void beforeAdvice(JoinPoint joinPoint, Loggable loggable) {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String timestamp = LocalDateTime.now().toString();

        log.info(BEFORE_ADVICE + "'{}'.'{}' started it's execution. Timestamp: {}",
                className,
                methodName,
                timestamp);
    }

    @AfterReturning("@annotation(loggable)")
    public void afterReturningAdvice(JoinPoint joinPoint, Loggable loggable) {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String timestamp = LocalDateTime.now().toString();

        log.info(AFTER_RETURNING_ADVICE + "{}.{} completed execution. Timestamp: {}",
                className, methodName, timestamp);
    }

    @Around("@annotation(timed)")
    public Object timedAdvice(ProceedingJoinPoint joinPoint, Timed timed) throws Throwable {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info(AROUND_ADVICE + "Execution Time of '{}'.'{}': {} ms.", className, methodName, executionTime);

        return proceed;
    }

}
