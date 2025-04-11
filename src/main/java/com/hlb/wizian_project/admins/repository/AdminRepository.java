package com.hlb.wizian_project.admins.repository;

import com.hlb.wizian_project.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE " +
            "LOWER(a.adminNm) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.loginId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.hpNo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(a.role) LIKE LOWER(CONCAT('%',:search, '%')) ORDER BY a.adminNo desc")
    Page<Admin> searchAdmins(@Param("search") String search, Pageable pageable);

    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    Optional<Admin> findByLoginId(String loginId);
}