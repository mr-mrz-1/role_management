package com.project.role_management.services;

import com.project.role_management.dto.locations.Coordinates;

public interface LocationService {
    String getAddressByCoordinate(Coordinates coordinate);
}
