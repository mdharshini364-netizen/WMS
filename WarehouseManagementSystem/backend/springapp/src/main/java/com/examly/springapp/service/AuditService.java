
package com.examly.springapp.service;

import com.examly.springapp.entity.AuditLog;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(String action, String entityType, Long entityId, String performedBy, String details) {
        AuditLog log = AuditLog.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .performedBy(performedBy)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    public AuditLog getAuditLogById(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog", "id", id.toString()));
    }

    public List<AuditLog> getAuditLogsByUser(String email) {
        return auditLogRepository.findByPerformedBy(email);
    }

    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    public List<AuditLog> getAuditLogsByEntity(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }
}

