package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.entity.Roles;
import com.project.role_management.services.RolesService;
import com.project.role_management.utils.response.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RolesController {

    private final RolesService rolesService;

    @PostMapping("/add/{roleName}")
    public ResponseEntity<?> addRole(@PathVariable String roleName) {
        Roles role = rolesService.addRole(roleName);
        return ResponseUtils.prepareResponse(HttpStatus.CREATED, SuccessConstants.ROLE_ADDED, role);
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllRoles() {
        List<Roles> roles = rolesService.getAllRoles();
        return ResponseUtils.prepareSuccessGetResponse(SuccessConstants.ROLE_FETCHED, roles);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable UUID id) {
        Roles role = rolesService.getRoleById(id);
        return ResponseUtils.prepareSuccessGetResponse(SuccessConstants.ROLE_FETCHED, role);
    }
}
