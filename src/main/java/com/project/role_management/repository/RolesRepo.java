package com.project.role_management.repository;

import com.project.role_management.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepo extends JpaRepository<Roles, UUID> {

    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);

    boolean existsByIdAndIsDeletedFalse(UUID roleId);

    Optional<Roles> findByIdAndIsDeletedFalse(UUID id);

    List<Roles> findAllByIsDeletedFalse();
}
