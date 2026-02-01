package com.project.role_management.utils.response;

import com.project.role_management.dto.responses.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity<?> prepareResponse(
            HttpStatus httpStatus, String message, Boolean isSuccess, Object data
    ){
        return ResponseEntity.status(httpStatus).body(
                ApiResponse.builder()
                        .message(message)
                        .success(isSuccess)
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<?> prepareResponse(
            HttpStatus httpStatus, String message, Object data
    ){
        return prepareResponse(httpStatus, message, true, data);
    }

    public static ResponseEntity<?> prepareSuccessGetResponse(
            String message, Object data
    ){
        return prepareResponse(HttpStatus.OK, message, true, data);
    }

    // This will return paged Response for any type of pagedObject
    public static <T> PagedResponse<T> toPagedResponse(Page<T> pageResult) {
        return PagedResponse.<T>builder()
                .content(pageResult.getContent())
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .isLastPage(pageResult.isLast())
                .build();
    }
}
