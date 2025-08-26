package com.example.springboothomework.annotation.aspect;

import com.example.springboothomework.annotation.AuditFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditFilterAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditFilterAspect.class);

    @Around("@annotation(auditFilter)")
    public Object around(ProceedingJoinPoint joinPoint, AuditFilter auditFilter) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Generate unique trace ID for this request
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        try {
            var body = joinPoint.getArgs();
            log.info("Request to controller start");

            Object result = joinPoint.proceed(); // Call the method

            long endTime = System.currentTimeMillis();
            log.info("Execution time: {} ms", endTime - startTime);

            return result;
        } catch (Throwable throwable) {
            log.error("Exception in {}", joinPoint.getSignature());
            return null;
        } finally {
            // Clean up MDC to prevent memory leaks
            MDC.remove("traceId");
        }
    }
}
