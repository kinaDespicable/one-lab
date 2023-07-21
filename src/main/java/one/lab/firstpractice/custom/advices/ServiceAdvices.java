package one.lab.firstpractice.custom.advices;

import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class ServiceAdvices {

    @AfterReturning("@annotation(loggable)")
    public void afterReturningAdvice(JoinPoint joinPoint, Loggable loggable) {

        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        Method method = getMethod(targetClass, methodName, joinPoint.getArgs());
        if (method != null) {
            Transactional transactionalAnnotation =
                    AnnotationUtils.findAnnotation(method, org.springframework.transaction.annotation.Transactional.class);

            if (transactionalAnnotation != null) {
                Propagation propagation = transactionalAnnotation.propagation();
                boolean readOnly = transactionalAnnotation.readOnly();
                log.info("'{}.{}' - propagation: {}, readOnly: {}", targetClass.getSimpleName(), methodName, propagation, readOnly);
            }
        }
    }

    private Method getMethod(Class<?> clazz, String methodName, Object[] args) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                return method;
            }
        }
        return null;
    }

}
