package com.project.role_management.servicesImpl;

import com.project.role_management.entity.Departments;
import com.project.role_management.exceptions.ApplicationErrorCode;
import com.project.role_management.exceptions.ApplicationException;
import com.project.role_management.repository.DepartmentsRepo;
import com.project.role_management.services.DepartmentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentsServiceImpl implements DepartmentsService {

    private final DepartmentsRepo departmentsRepo;

    public DepartmentsServiceImpl(DepartmentsRepo departmentsRepo) {
        this.departmentsRepo = departmentsRepo;
    }

    @Override
    public Departments addDepartment(String departmentName) {
        log.info("DepartmentServiceImpl, addDepartment() method started for : {}", departmentName);
        if(departmentName==null || departmentName.trim().isEmpty()){
            throw new ApplicationException(ApplicationErrorCode.DEPARTMENT_NAME_EMPTY);
        }

        if(departmentsRepo.existsByNameIgnoreCaseAndIsDeletedFalse(departmentName.trim())){
            throw new ApplicationException(ApplicationErrorCode.DEPARTMENT_ALREADY_EXISTS);
        }

        Departments departments = new Departments();
        departments.setName(departmentName);
        Departments savedDepartment = departmentsRepo.save(departments);
        log.info("DepartmentServiceImpl, addDepartment() method finished for : {}", departmentName);
        return savedDepartment;
    }

}
