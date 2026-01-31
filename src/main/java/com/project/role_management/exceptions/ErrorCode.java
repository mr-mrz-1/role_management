package com.project.role_management.exceptions;

public interface ErrorCode {
    String getMessage();
    String getTitle();
    int getStatusCode();
    String getErrorCode();
}
