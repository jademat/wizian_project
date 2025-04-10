package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.CourseStdntInstListDTO;
import com.hlb.wizian_project.domain.LectInfoInstListDTO;
import com.hlb.wizian_project.instructors.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins= {"http://localhost:3000"})
@Slf4j
@RestController
@RequestMapping("/api/inst/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping("/courseInfo/list/{cpg}")
    public ResponseEntity<?> allCourseList(Authentication authentication, @PathVariable int cpg) {
        // 로그인 된 강사 정보 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loginInst = userDetails.getUsername();

        CourseStdntInstListDTO courseInfoDTO = courseService.findOneLectListStdnt(cpg, loginInst);

        return new ResponseEntity<>(courseInfoDTO, HttpStatus.OK);
    }
}
