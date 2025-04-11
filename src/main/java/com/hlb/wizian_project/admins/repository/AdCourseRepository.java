package com.hlb.wizian_project.admins.repository;

import com.hlb.wizian_project.admins.domain.CoursesDTO;
import com.hlb.wizian_project.domain.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdCourseRepository extends JpaRepository<Courses, Integer> {


    @Query("SELECT c FROM Courses c " +
            "WHERE LOWER(c.courDept) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.courYear) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.courNm) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "ORDER BY c.courNo DESC")
    Page<CoursesDTO> searchCourses(@Param("search") String search, Pageable pageable);

    @Query("SELECT c FROM Courses c ORDER BY c.courNo DESC")
    Page<CoursesDTO> findAllProjectedBy(Pageable pageable);


}