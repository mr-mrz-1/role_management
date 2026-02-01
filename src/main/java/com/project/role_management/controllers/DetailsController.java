package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.dto.AddDetailsDto;
import com.project.role_management.dto.responses.DetailsProjection;
import com.project.role_management.entity.Details;
import com.project.role_management.services.DetailsService;
import com.project.role_management.utils.filters.Filter;
import com.project.role_management.dto.responses.PagedResponse;
import com.project.role_management.utils.response.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/details")
@RequiredArgsConstructor
public class DetailsController {

    private final DetailsService detailsService;

    @PostMapping("/add")
    public ResponseEntity<?> addPersonalDetails(@Valid @RequestBody AddDetailsDto detailsData){
        Details details = detailsService.addDetails(detailsData);
        return ResponseUtils.prepareResponse(HttpStatus.CREATED, SuccessConstants.DETAILS_ADDED, details);
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getDepartments(
            @RequestParam(required = false) String filter
    ) {
        Filter filterObj =  Filter.encodeFilter(filter);
        PagedResponse<DetailsProjection> details = detailsService.getAllDetails(filterObj);
        return ResponseUtils.prepareSuccessGetResponse(SuccessConstants.DETAILS_FETCHED, details);
    }
}
