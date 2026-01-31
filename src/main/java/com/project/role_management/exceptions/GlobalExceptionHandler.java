package com.project.role_management.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid Request Content");
        problem.setTitle("Validation Failed");
        problem.setType(URI.create("about:blank"));

        // We cast WebRequest to ServletWebRequest to get the URI
        if (request instanceof ServletWebRequest servletWebRequest) {
            problem.setInstance(URI.create(servletWebRequest.getRequest().getRequestURI()));
        }

        problem.setProperty("errors", validationErrors);

        return ResponseEntity.status(status).body(problem);
    }

    // Handle ApplicationException (CustomException)
    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleException(ApplicationException ex, HttpServletRequest request) {

        ErrorCode code = ex.getErrorCode();

        log.warn("ApplicationException triggered at [{} {}] | ErrorCode: [{}] | Message: {}",
                request.getMethod(),
                request.getRequestURI(),
                code.getErrorCode(),
                ex.getMessage()
//               , ex
        );
        // uncomment exception above if want to display whole exception

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(code.getStatusCode()),
                code.getMessage()
        );

        problem.setTitle(code.getTitle());
        problem.setType(URI.create("about:blank"));

        problem.setInstance(URI.create(request.getRequestURI()));

        problem.setProperty("errorCode", code.getErrorCode());

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
