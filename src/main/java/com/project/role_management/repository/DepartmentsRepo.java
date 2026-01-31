package com.project.role_management.repository;

import com.project.role_management.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentsRepo extends JpaRepository<Departments, UUID> {
}
