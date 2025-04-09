package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.instructors.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"http://localhost:3000"})
@Slf4j
@RestController
@RequestMapping("/api/inst/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;


    @GetMapping("/list/{sortYear}/{sortWeek}/{findkey}/{cpg}")
    public ResponseEntity<?> list(@PathVariable int cpg, @PathVariable String sortYear,
                                  @PathVariable String sortWeek, @PathVariable String findkey) {
        CourseInstListDTO classListDTO = classService.findClass(cpg, sortYear, sortWeek, findkey);

        return new ResponseEntity<>(classListDTO, HttpStatus.OK);
    }
}
