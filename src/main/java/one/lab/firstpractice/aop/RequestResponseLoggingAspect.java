package one.lab.firstpractice.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.annotation.LoggableRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Slf4j
@Aspect
@Component
public class RequestResponseLoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Around("@annotation(loggableRequest)")
    public Object logRequestResponse(ProceedingJoinPoint joinPoint, LoggableRequest loggableRequest) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String username = getUsernameFromAuthentication();

        log.info("Request received from IP address: {}", ipAddress);
        log.info("Requested URL: {}", url);
        if (username != null) {
            log.info("Username: {}", username);
        }
        log.info("HTTP Method: {}", request.getMethod());
        log.info("Headers: {}", getRequestHeaders(request));

        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity<?> responseEntity) {
            log.info("Response Status: {}", responseEntity.getStatusCode());
            log.info("Response Headers: {}", responseEntity.getHeaders());
            log.info("Response Body: {}", getResponseBody(responseEntity));

        }

        return result;
    }

    private String getRequestHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.append(headerName).append(": ").append(headerValue).append("\n");
        }
        return headers.toString();
    }

    private String getUsernameFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    private String getResponseBody(ResponseEntity<?> responseEntity) {
        try {
            return objectMapper.writeValueAsString(responseEntity.getBody());
        } catch (Exception e) {
            // Handle JSON serialization exception, if any
            return "Unable to extract response body as JSON.";
        }
    }
}