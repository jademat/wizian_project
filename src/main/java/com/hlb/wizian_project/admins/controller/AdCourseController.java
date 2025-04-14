package com.hlb.wizian_project.admins.controller;

import com.hlb.wizian_project.admins.domain.CoursesDTO;
import com.hlb.wizian_project.admins.repository.AdCourseRepository;
import com.hlb.wizian_project.admins.service.AdCourseService;
import com.hlb.wizian_project.domain.Courses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins/coulec")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequiredArgsConstructor
@Slf4j
public class AdCourseController {

    private final AdCourseService courseService;
    private final AdCourseRepository courseRepository;

    @GetMapping("/coulist")
    public Page<CoursesDTO> getCourses(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "courNo", direction = Sort.Direction.DESC) Pageable pageable) {
        return courseService.getCourses(search, pageable);
    }


    @GetMapping("/detail/{courNo}")
    public ResponseEntity<?> getCourse(@PathVariable int courNo) {
        Optional<?> course = courseService.getCoursesById(courNo);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCourse(@RequestBody Courses course) {
        try {
            courseService.newCourse(course);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("서버 오류 발생");
        }
    }

    @Transactional
    @DeleteMapping("/delete/{courNo}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courNo) {
        try {
            courseService.deleteCourseById(courNo);
            return ResponseEntity.ok().body("삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("삭제 실패");
        }
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<Courses>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }


}
