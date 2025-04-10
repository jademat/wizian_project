package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Long> {

    // findAllCourse
    Page<Courses> findBy(Pageable pageable);

    Page<Courses> findByCourYearAndCourWeek(Pageable pageable, String sortYear, String sortWeek);
    Page<Courses> findByCourYear(Pageable pageable, String sortYear);
    Page<Courses> findByCourWeek(Pageable pageable, String sortWeek);
    Page<Courses> findByCourNmContains(Pageable pageable, String findkey);
}
