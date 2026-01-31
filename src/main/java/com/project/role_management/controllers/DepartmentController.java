package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.entity.Departments;
import com.project.role_management.services.DepartmentsService;
import com.project.role_management.utils.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentsService departmentsService;

    public DepartmentController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @PostMapping("/add/{departmentName}")
    public ResponseEntity<?> addDepartment(@PathVariable String departmentName) {
        Departments savedDepartments = departmentsService.addDepartment(departmentName);
        return ResponseUtils.prepareResponse(HttpStatus.CREATED, SuccessConstants.DEPARTMENT_ADDED, savedDepartments);
    }

    @GetMapping("/get/{departmentId}")
    public ResponseEntity<?> getDepartmentById(@PathVariable UUID departmentId) {
        Departments department = departmentsService.getDepartmentById(departmentId);
        return ResponseUtils.prepareSuccessGetResponse(SuccessConstants.DEPARTMENT_FETCHED, department);
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getDepartments() {
        List<Departments> departments = departmentsService.getAllDepartments();
        return ResponseUtils.prepareSuccessGetResponse(SuccessConstants.DEPARTMENT_FETCHED, departments);
    }
}
