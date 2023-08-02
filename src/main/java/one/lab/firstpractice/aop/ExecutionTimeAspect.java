package one.lab.firstpractice.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(one.lab.firstpractice.annotation.ComputableFutureLogger)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint ) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.warn(
                "Execution time of {} in {} ms",
                joinPoint.getSignature().toShortString(),
                executionTime
        );

        return result;
    }
}