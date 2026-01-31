package com.project.role_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "personal_details")
public class PersonalDetails {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "address", nullable = false)
    private String address;

    // Also we can replace with its id and manage in separate table
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "department_id", nullable = false)
    private UUID departmentId;

    @Column(name = "role_id", nullable = false)
    private UUID roleId;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}
