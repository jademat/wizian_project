package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.LectInfoInstListDTO;
import com.hlb.wizian_project.instructors.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"http://localhost:3000"})
@Slf4j
@RestController
@RequestMapping("/api/inst/course")
@RequiredArgsConstructor
public class CourseController {

    private final ClassService classService;


    @GetMapping("/allCourse/list/{sortYear}/{sortWeek}/{findkey}/{cpg}")
    public ResponseEntity<?> allCourseList(@PathVariable int cpg, @PathVariable String sortYear,
                                  @PathVariable String sortWeek, @PathVariable String findkey) {
        CourseInstListDTO classListDTO = classService.findAllCourse(cpg, sortYear, sortWeek, findkey);

        return new ResponseEntity<>(classListDTO, HttpStatus.OK);
    }


    @GetMapping("/allClass/list/{sortLoc}/{sortStatus}/{sortInstNm}/{findkey}/{cpg}")
    public ResponseEntity<?> allclassList(@PathVariable int cpg, @PathVariable String sortLoc,
                                  @PathVariable String sortStatus, @PathVariable String sortInstNm, @PathVariable String findkey) {
        LectInfoInstListDTO classListDTO = classService.findAllClass(cpg, sortLoc, sortStatus, sortInstNm, findkey);

        return new ResponseEntity<>(classListDTO, HttpStatus.OK);
    }
}
