package com.project.role_management.dto.responses;

import java.util.UUID;

public interface DetailsProjection {
    UUID getId();
    String getName();
    Integer getAge();
    String getMobileNumber();
    String getCity();
    String getAddress();
    UUID getRoleId();
    String getRoleName();
    UUID getDepartmentId();
    String getDepartmentName();
}
