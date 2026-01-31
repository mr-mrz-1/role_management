package com.project.role_management.dto;

import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddDetailsDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name should be between 2 to 50 characters")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age can not be negative")
    @Max(value = 100, message = "Age can not be greater than 100")
    private Integer age;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Address is required")
    @Size(min = 2, max = 100, message = "Address must be between 2 to 100 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 30, message = "City name must be between 2 to 30 characters")
    private String city;

    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;

}
