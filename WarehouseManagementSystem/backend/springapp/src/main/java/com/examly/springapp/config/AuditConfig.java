
package com.examly.springapp.config;

import com.examly.springapp.entity.AuditLog;
import com.examly.springapp.repository.AuditLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Aspect
@Configuration
public class AuditConfig {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @AfterReturning(pointcut = "execution(* com.examly.springapp.service.*.*(..))", returning = "result")
    public void logServiceCall(JoinPoint joinPoint, Object result) {
        try {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();

            // Only log create, update, delete operations
            if (methodName.startsWith("create") || methodName.startsWith("update") ||
                methodName.startsWith("delete") || methodName.startsWith("register") ||
                methodName.startsWith("login")) {

                String performedBy = "anonymous";
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
                    performedBy = auth.getName();
                }

                AuditLog log = AuditLog.builder()
                        .action(methodName)
                        .entityType(className.replace("Service", ""))
                        .performedBy(performedBy)
                        .details("Method: " + methodName + " in " + className)
                        .timestamp(LocalDateTime.now())
                        .build();

                auditLogRepository.save(log);
            }
        } catch (Exception e) {
            // Silently ignore audit logging errors
        }
    }
}

