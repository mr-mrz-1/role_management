package com.project.role_management.repository;

import com.project.role_management.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DetailsRepo extends JpaRepository<Details, UUID> {
}
