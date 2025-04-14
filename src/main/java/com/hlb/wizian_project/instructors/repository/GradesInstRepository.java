package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.Grades;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GradesInstRepository extends JpaRepository<Grades, Long> {

    @Query("select g from Grades g where g.lectApply.lectInfo.lectNo = :lectNo")
    Page<Grades> findByLectApply_LectInfo_LectNo(int lectNo, Pageable pageable);
}
