package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.domain.*;
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
    public ResponseEntity<?> courseInfo(Authentication authentication, @PathVariable int cpg) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        CourseStdntInstListDTO courseInfoDTO = courseService.findOneLectListStdnt(cpg, loginInst);

        return new ResponseEntity<>(courseInfoDTO, HttpStatus.OK);
    }


    @GetMapping("/courseStudent/list/{sortStatus}/{sortGender}/{findkey}/{cpg}")
    public ResponseEntity<?> courseStudent(Authentication authentication, @PathVariable int cpg,
                                @PathVariable String sortStatus, @PathVariable String sortGender, @PathVariable String findkey) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        CourseStdntApplyListDTO courseStudentDTO = courseService.findStudentListApplyInfo(cpg, sortStatus, sortGender, findkey, loginInst);

        return new ResponseEntity<>(courseStudentDTO, HttpStatus.OK);
    }


    @GetMapping("/courseAttend/list/{sortStatus}/{sortDate}/{findkey}/{cpg}")
    public ResponseEntity<?> courseAttend(Authentication authentication, @PathVariable int cpg,
                                           @PathVariable String sortStatus, @PathVariable String sortDate, @PathVariable String findkey) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        CourseStdntApplyListDTO courseStudentDTO = courseService.findStudentListApplyInfoAttendList(cpg, sortStatus, sortDate, findkey, loginInst);

        return new ResponseEntity<>(courseStudentDTO, HttpStatus.OK);
    }


    @GetMapping("/courseProblem/list/{sortYear}/{sortHalf}/{findkey}/{cpg}")
    public ResponseEntity<?> courseProblem(Authentication authentication, @PathVariable int cpg,
                                          @PathVariable String sortYear, @PathVariable String sortHalf, @PathVariable String findkey) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        CourseProblemListInstDTO courseStudentDTO = courseService.findProblemInfoList(cpg, sortYear, sortHalf, findkey, loginInst);

        return new ResponseEntity<>(courseStudentDTO, HttpStatus.OK);
    }


    @GetMapping("/courseGrade/list/{sortAttend}/{sortProNm}/{findkey}/{findkeySub}/{cpg}")
    public ResponseEntity<?> courseGrade(Authentication authentication, @PathVariable int cpg,
                                           @PathVariable String sortAttend, @PathVariable String sortProNm, @PathVariable String findkey, @PathVariable String findkeySub) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        CourseGradeListInstDTO courseGradeDTO = courseService.findGradeInfoList(cpg, sortAttend, sortProNm, findkey, findkeySub, loginInst);

        return new ResponseEntity<>(courseGradeDTO, HttpStatus.OK);
    }


    @GetMapping("/courseGrade/stdentList/{studentNo}")
    public ResponseEntity<?> courseGradeInfo(Authentication authentication, @PathVariable int studentNo) {
        log.info(">>>>>> courseGradeInfo 호출");
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        AttendGradeDTO courseGradeDTO = courseService.findStudentAttendInfo(studentNo, loginInst);

        return new ResponseEntity<>(courseGradeDTO, HttpStatus.OK);
    }
}
