package com.project.role_management.repository;

import com.project.role_management.dto.responses.DetailsProjection;
import com.project.role_management.entity.Details;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DetailsRepo extends JpaRepository<Details, UUID> {

        // FIX 1: Add explicit "LEFT JOIN" with aliases (r, dept)
        @Query(value = """
        SELECT
            d.id AS id,
            d.name AS name,
            d.age AS age,
            d.mobile_number AS mobileNumber,
            d.city AS city,
            d.address AS address,
            d.role_id AS roleId,
            d.department_id AS departmentId,
            r.name AS roleName,
            dept.name AS departmentName
        FROM personal_details d
        LEFT JOIN role r ON d.role_id = r.id
        LEFT JOIN department dept ON d.department_id = dept.id
        WHERE d.is_deleted = false 
        AND (:roleId IS NULL OR d.role_id = :roleId)
        AND (:deptId IS NULL OR d.department_id = :deptId)
        AND (:city IS NULL OR d.city = :city)
        AND (:startAge IS NULL OR d.age >= :startAge)
        AND (:endAge IS NULL OR d.age <= :endAge)
        AND (:search IS NULL OR (
            LOWER(d.name) LIKE LOWER(:search) OR 
            d.mobile_number LIKE :search OR 
            LOWER(d.address) LIKE LOWER(:search) OR
            LOWER(r.name) LIKE LOWER(:search) OR
            LOWER(dept.name) LIKE LOWER(:search)
        ))
        """,
                countQuery = """
        SELECT count(*) 
        FROM personal_details d
        LEFT JOIN role r ON d.role_id = r.id
        LEFT JOIN department dept ON d.department_id = dept.id
        WHERE d.is_deleted = false 
        AND (:roleId IS NULL OR d.role_id = :roleId)
        AND (:deptId IS NULL OR d.department_id = :deptId)
        AND (:city IS NULL OR d.city = :city)
        AND (:startAge IS NULL OR d.age >= :startAge)
        AND (:endAge IS NULL OR d.age <= :endAge)
        AND (:search IS NULL OR (
            LOWER(d.name) LIKE LOWER(:search) OR 
            d.mobile_number LIKE :search OR 
            LOWER(d.address) LIKE LOWER(:search) OR
            LOWER(r.name) LIKE LOWER(:search) OR
            LOWER(dept.name) LIKE LOWER(:search)
        ))
        """,
                nativeQuery = true)
        Page<DetailsProjection> getDetailsWithSearchAndFilter(
                @Param("roleId") UUID roleId,
                @Param("deptId") UUID departmentId,
                @Param("city") String city,
                @Param("startAge") Integer startAge,
                @Param("endAge") Integer endAge,
                @Param("search") String search,
                Pageable pageable
        );


}
