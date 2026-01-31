package com.project.role_management.controllers;

import com.project.role_management.contants.SuccessConstants;
import com.project.role_management.dto.locations.Coordinates;
import com.project.role_management.services.LocationService;
import com.project.role_management.utils.response.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/get/address")
    public ResponseEntity<?> getAddress(@RequestBody Coordinates coordinate){
        String address = locationService.getAddressByCoordinate(coordinate);
        return ResponseUtils.prepareSuccessGetResponse(
                SuccessConstants.LOCATION_FETCHED, address
        );
    }
}
