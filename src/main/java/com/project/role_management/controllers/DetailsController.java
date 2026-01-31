package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.dto.AddDetailsDto;
import com.project.role_management.entity.Details;
import com.project.role_management.services.DetailsService;
import com.project.role_management.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/details")
public class DetailsController {

    private final DetailsService detailsService;

    public DetailsController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPersonalDetails(@Valid @RequestBody AddDetailsDto detailsData){
        Details details = detailsService.addDetails(detailsData);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message(SuccessConstants.DETAILS_ADDED)
                        .success(true)
                        .data(details)
                        .build()
        );
    }
}
