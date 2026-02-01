package com.project.role_management.services;

import com.project.role_management.dto.AddDetailsDto;
import com.project.role_management.dto.responses.DetailsProjection;
import com.project.role_management.entity.Details;
import com.project.role_management.utils.filters.Filter;
import com.project.role_management.dto.responses.PagedResponse;

public interface DetailsService {
    
    Details addDetails(AddDetailsDto detailsData);

    PagedResponse<DetailsProjection> getAllDetails(Filter filter);
}
