package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.CoursesDTO;
import com.hlb.wizian_project.admins.repository.AdCourseRepository;
import com.hlb.wizian_project.domain.Courses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdCourseServiceImpl implements AdCourseService {

    private final AdCourseRepository courseRepository;

    @Override
    public Page<CoursesDTO> getCourses(String search, Pageable pageable) {

        return (search == null || search.trim().isEmpty())
                ? courseRepository.findAllProjectedBy(pageable)
                : courseRepository.searchCourses(search, pageable);
    }

    @Override
    public Optional<Courses> getCoursesById(int courNo) {
        return courseRepository.findById(courNo);
    }

    @Override
    public Courses newCourse(Courses course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int courNo) {
        // 1. 삭제하려는 과정 찾기
        Courses course = courseRepository.findById(courNo)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // 2. 삭제
        courseRepository.delete(course);  // 데이터 삭제만 처리
    }

}


