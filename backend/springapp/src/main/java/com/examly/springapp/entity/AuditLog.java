
package com.examly.springapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;        // Who performed the action

    @Column(nullable = false)
    private String action;          // CREATE, UPDATE, DELETE, LOGIN, LOGOUT, ACTIVATE, DEACTIVATE, ROLE_CHANGE

    @Column(name = "entity_type", nullable = false)
    private String entityType;      // USER, PRODUCT, ORDER, INVENTORY, PICK_TASK, SHIPMENT, BIN

    @Column(name = "entity_id")
    private String entityId;        // ID of the affected record

    @Column(columnDefinition = "TEXT")
    private String details;         // Human-readable description of what happened

    @Column(name = "ip_address")
    private String ipAddress;       // IP address of the requester

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}

