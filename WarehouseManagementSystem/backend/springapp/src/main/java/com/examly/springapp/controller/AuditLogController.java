package com.examly.springapp.controller;
import com.examly.springapp.entity.AuditLog;
import com.examly.springapp.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditService auditService;

    // GET /api/audit-logs
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        return ResponseEntity.ok(auditService.getAllAuditLogs());
    }

    // GET /api/audit-logs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> getAuditLogById(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getAuditLogById(id));
    }

    // GET /api/audit-logs/user/{email}
    @GetMapping("/user/{email}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(@PathVariable String email) {
        return ResponseEntity.ok(auditService.getAuditLogsByUser(email));
    }

    // GET /api/audit-logs/action/{action}
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable String action) {
        return ResponseEntity.ok(auditService.getAuditLogsByAction(action));
    }

    // GET /api/audit-logs/entity/{entityType}
    @GetMapping("/entity/{entityType}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByEntity(@PathVariable String entityType) {
        return ResponseEntity.ok(auditService.getAuditLogsByEntity(entityType));
    }
}

