package com.project.role_management.servicesImpl;

import com.project.role_management.entity.Roles;
import com.project.role_management.exceptions.ApplicationErrorCode;
import com.project.role_management.exceptions.ApplicationException;
import com.project.role_management.repository.RolesRepo;
import com.project.role_management.services.RolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesRepo rolesRepo;

    @Override
    public Roles addRole(String roleName) {

        log.info("RolesServiceImpl, addRole() method started for adding role : {}", roleName);
        if(roleName==null || roleName.trim().isEmpty()){
            throw new ApplicationException(ApplicationErrorCode.ROLE_NAME_EMPTY);
        }

        if(rolesRepo.existsByNameIgnoreCaseAndIsDeletedFalse(roleName.trim())){
            throw new ApplicationException(ApplicationErrorCode.ROLE_ALREADY_EXISTS);
        }

        Roles role = new Roles();
        role.setName(roleName.trim());
        Roles savedRole = rolesRepo.save(role);

        log.info("RolesServiceImpl, addRole() method finished for adding role : {}", role);
        return savedRole;
    }

}
