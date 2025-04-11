package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.CoursesDTO;
import com.hlb.wizian_project.domain.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdCourseService {
    Page<CoursesDTO> getCourses(String search, Pageable pageable);
    Optional<Courses> getCoursesById(int courNo);
    Courses newCourse(Courses course);
    void deleteCourseById(int courNo);

}
