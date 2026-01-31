package com.project.role_management.repository;

import com.project.role_management.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolesRepo extends JpaRepository<Roles, UUID> {
}
