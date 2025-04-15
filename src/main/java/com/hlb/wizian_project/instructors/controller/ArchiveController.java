package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.domain.*;
import com.hlb.wizian_project.instructors.service.ArchiveService;
import com.hlb.wizian_project.instructors.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins= {"http://localhost:3000"})
@Slf4j
@RestController
@RequestMapping("/api/inst/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    @GetMapping("/myProblem/list/{sortYear}/{sortHalf}/{findkey}/{cpg}")
    public ResponseEntity<?> myProblem(Authentication authentication, @PathVariable int cpg,
                                           @PathVariable String sortYear, @PathVariable String sortHalf, @PathVariable String findkey) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        MyProblemListInstDTO myProblemDto = archiveService.archiveMyProblem(cpg, sortYear, sortHalf, findkey, loginInst);

        return new ResponseEntity<>(myProblemDto, HttpStatus.OK);
    }


    @GetMapping("/myProblem/countSubmit/{infoNo}/{infoNm}")
    public ResponseEntity<?> myProblemCountSubmit(Authentication authentication,
                                       @PathVariable int infoNo, @PathVariable String infoNm) {
        // 로그인 된 강사 정보 추출
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //String loginInst = userDetails.getUsername();
        String loginInst = "김지훈";

        Map<String, Long> countData = archiveService.archiveMyProblemCountSubmit(infoNo, infoNm, loginInst);

        return new ResponseEntity<>(countData, HttpStatus.OK);
    }

    // TODO: myBoard 부터 해야함 - 프로젝트 기간종료로 인한 기능 개발 중지
    // FIXME : 프론트 미 완성
}
