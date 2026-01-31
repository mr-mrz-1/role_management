package com.project.role_management.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle ApplicationException (CustomException)
    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleException(ApplicationErrorCode ex, HttpServletRequest request) {

        // create the ProblemDetail object using the status and message
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(ex.getStatusCode()), ex.getMessage());

        // setting standard fields
        problem.setTitle(ex.getTitle());
        problem.setType(URI.create("about:blank")); // Standard default

        // set the instance -> Url that caused the error
        problem.setInstance(URI.create(request.getRequestURI()));

        // Set custom error code ( defined by our self )
        problem.setProperty("errorCode", ex.getErrorCode());

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex) {

        log.error("Unexpected error: ", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected internal error occurred."
        );

        problemDetail.setTitle("Internal Server Error");
        return problemDetail;
    }

}
