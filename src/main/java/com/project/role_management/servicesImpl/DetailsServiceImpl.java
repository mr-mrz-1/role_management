package com.project.role_management.servicesImpl;

import com.project.role_management.dto.AddDetailsDto;
import com.project.role_management.dto.responses.DetailsProjection;
import com.project.role_management.entity.Details;
import com.project.role_management.exceptions.ApplicationErrorCode;
import com.project.role_management.exceptions.ApplicationException;
import com.project.role_management.repository.DepartmentsRepo;
import com.project.role_management.repository.DetailsRepo;
import com.project.role_management.repository.RolesRepo;
import com.project.role_management.services.DetailsService;
import com.project.role_management.utils.filters.Filter;
import com.project.role_management.dto.responses.PagedResponse;
import com.project.role_management.utils.response.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailsService {

    private final DetailsRepo detailsRepo;
    private final RolesRepo rolesRepo;
    private final DepartmentsRepo departmentsRepo;

    @Override
    public Details addDetails(AddDetailsDto detailsData) {
        log.info("DetailsServiceImpl, addDetails() method starts for : {}", detailsData);

        if(!rolesRepo.existsByIdAndIsDeletedFalse(detailsData.getRoleId())){
            throw new ApplicationException(ApplicationErrorCode.ROLES_NOT_FOUND);
        }

        if(!departmentsRepo.existsByIdAndIsDeletedFalse(detailsData.getDepartmentId())){
            throw new ApplicationException(ApplicationErrorCode.DEPARTMENT_NOT_FOUND);
        }

        Details details = new Details();

        details.setName(detailsData.getName().trim());
        details.setAge(detailsData.getAge());
        details.setMobileNumber(detailsData.getMobileNumber());
        details.setAddress(detailsData.getAddress().trim());
        details.setCity(detailsData.getCity().trim());
        details.setRoleId(detailsData.getRoleId());
        details.setDepartmentId(detailsData.getDepartmentId());

        Details savedDetails = detailsRepo.save(details);
        log.info("DetailsServiceImpl, addDetails() method finished for : {}", detailsData.getName());
        return savedDetails;
    }

    @Override
    public PagedResponse<DetailsProjection> getAllDetails(Filter filter){
        log.info("DetailsServiceImpl, getAllDetails() method starts");
        Pageable pageable = Filter.createPageable(filter);

        String search = null;
        if(filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            search = "%" + filter.getSearch().trim() + "%";
        }

        Page<DetailsProjection> detailsPage = detailsRepo.getDetailsWithSearchAndFilter(
                filter.getRoleId(),
                filter.getDepartmentId(),
                filter.getCity(),
                filter.getStartAge(),
                filter.getEndAge(),
                search,
                pageable
        );

        return ResponseUtils.toPagedResponse(detailsPage);
    }
}
