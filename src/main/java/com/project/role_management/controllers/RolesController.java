package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.entity.Roles;
import com.project.role_management.services.RolesService;
import com.project.role_management.utils.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RolesController {

    private final RolesService rolesService;

    @PostMapping("/add/{roleName}")
    public ResponseEntity<?> addRole(@PathVariable String roleName) {
        Roles role = rolesService.addRole(roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message(SuccessConstants.ROLE_ADDED)
                        .success(true)
                        .data(role)
                        .build()
        );
    }

}
