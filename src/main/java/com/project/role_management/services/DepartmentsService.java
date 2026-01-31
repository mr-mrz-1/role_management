package com.project.role_management.services;

import com.project.role_management.entity.Departments;

import java.util.List;
import java.util.UUID;

public interface DepartmentsService {

    Departments addDepartment(String departmentName);

    Departments getDepartmentById(UUID id);

    List<Departments> getAllDepartments();

}
