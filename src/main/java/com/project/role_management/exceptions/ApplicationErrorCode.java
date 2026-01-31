package com.project.role_management.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationErrorCode implements ErrorCode {

    private final String title;
    private final String message;
    private final int statusCode;
    private final String errorCode;
}
