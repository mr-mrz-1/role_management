package com.project.role_management.repository;

import com.project.role_management.entity.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalDetailsRepo extends JpaRepository<PersonalDetails, UUID> {
}
