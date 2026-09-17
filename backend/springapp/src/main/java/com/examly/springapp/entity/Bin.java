
package com.examly.springapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bin_code", nullable = false, unique = true)
    private String binCode;

    @Column(name = "zone")
    private String zone;

    @Column(name = "aisle")
    private String aisle;

    @Column(name = "rack")
    private String rack;

    @Column(name = "level")
    private String level;

    @Column(name = "capacity")
    private Integer capacity;

    @Builder.Default
    @Column(name = "occupied")
    private Boolean occupied = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

