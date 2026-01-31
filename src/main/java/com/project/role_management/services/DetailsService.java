package com.project.role_management.services;

import com.project.role_management.dto.AddDetailsDto;
import com.project.role_management.entity.Details;

public interface DetailsService {
    
    Details addDetails(AddDetailsDto detailsData);
}
