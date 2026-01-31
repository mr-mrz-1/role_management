package com.project.role_management.utils.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.role_management.exceptions.ApplicationErrorCode;
import com.project.role_management.exceptions.ApplicationException;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data
public class Filter {
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private Sort.Direction sortOrder;
    private String search = null;
    private Integer startAge = null;
    private Integer endAge = null;
    private String city = null;
    private UUID roleId = null;
    private UUID departmentId = null;

    public static Pageable createPageable(Filter filter) {
        // 1. Defaults
        int page = (filter.getPageNo() != null) ? filter.getPageNo() - 1 : 0;
        int size = (filter.getPageSize() != null) ? filter.getPageSize() : 10;


//        Sort sort = Sort.unsorted();
//        if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
//            // Default to ASC if sortOrder is missing
//            Sort.Direction direction = (filter.getSortOrder() != null) ? filter.getSortOrder() : Sort.Direction.ASC;
//            sort = Sort.by(direction, filter.getSortBy());
//        }

        Sort.Direction direction = (filter.getSortOrder() != null) ? filter.getSortOrder() : Sort.Direction.DESC;
        String sortBy = filter.getSortBy() != null ? filter.getSortBy() : "created_at";
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }

    public static Filter encodeFilter(String filter) {
        Filter filterObject = new Filter();
        try {
            if (filter != null && !filter.isEmpty()) {
                String decodeFilter = URLDecoder.decode(filter, StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                filterObject = objectMapper.readValue(decodeFilter, Filter.class);
            }

            return filterObject;
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ApplicationErrorCode.INVALID_FILTER);
        }
    }
}
