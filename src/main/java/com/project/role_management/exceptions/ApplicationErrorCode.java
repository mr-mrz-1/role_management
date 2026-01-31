package com.project.role_management.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {

    ROLE_NAME_EMPTY("INVALID REQUEST", "Roles name can not be empty or blank", 400, "role_400_1"),
    ROLE_ALREADY_EXISTS("INVALID REQUEST", "Role already exists with this name", 400, "role_400_2"),
    DEPARTMENT_NAME_EMPTY("INVALID REQUEST", "Department name can not be empty or blank", 400, "department_400_1"),
    DEPARTMENT_ALREADY_EXISTS("INVALID REQUEST", "Department already exists with this name", 400, "department_400_2"),
    ROLES_NOT_FOUND("INVALID REQUEST", "Roles not found", 404 , "role_404_1" ),
    DEPARTMENT_NOT_FOUND("INVALID REQUEST", "Department not found", 404 , "department_404_1" ), 
    DETAILS_NOT_FOUND("INVALID REQUEST", "Details not found", 404 , "details_404_1" ),
    INVALID_FILTER("INVALID REQUEST", "Invalid Data Provided",400 , "req_400_1" ),

    UNABLE_TO_LOCATE("SERVICE ERROR", "Unable To Locate", 500 , "service_500_1" ),;

    private final String title;
    private final String message;
    private final int statusCode;
    private final String errorCode;
}
