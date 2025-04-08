package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.Courses;
import com.hlb.wizian_project.domain.CourseInstDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Courses, Long> {

    Page<Courses> findBy(Pageable pageable);
}
