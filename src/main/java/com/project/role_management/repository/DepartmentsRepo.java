package com.project.role_management.repository;

import com.project.role_management.entity.Departments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentsRepo extends JpaRepository<Departments, UUID> {

    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String departmentsName);

    boolean existsByIdAndIsDeletedFalse(@NotNull @NotEmpty UUID departmentId);

    List<Departments> findAllByIsDeletedFalse();

    Optional<Departments> findByIdAndIsDeletedFalse(UUID id);
}
