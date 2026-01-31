package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.entity.Departments;
import com.project.role_management.services.DepartmentsService;
import com.project.role_management.utils.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentsService departmentsService;

    public DepartmentController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @PostMapping("/add/{departmentName}")
    public ResponseEntity<?> addDepartment(@PathVariable String departmentName){
        Departments savedDepartments = departmentsService.addDepartment(departmentName);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message(SuccessConstants.DEPARTMENT_ADDED)
                        .success(true)
                        .data(savedDepartments)
                        .build()
        );
    }
}
