package com.project.role_management.services;

import java.util.List;
import java.util.UUID;

import com.project.role_management.entity.Roles;

public interface RolesService {

    Roles addRole(String roleName);

    Roles getRoleById(UUID id);

    List<Roles> getAllRoles();
}
