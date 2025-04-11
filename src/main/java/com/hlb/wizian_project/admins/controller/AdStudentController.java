package com.hlb.wizian_project.admins.controller;

import com.hlb.wizian_project.admins.service.AdStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins/student")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequiredArgsConstructor
@Slf4j
public class AdStudentController {

    private final AdStudentService adStudentService;

//    @GetMapping("/list")
//    public Page<Studnt> getStudnt(
//            @RequestParam(required = false) String search,
//            @PageableDefault(size = 10) Pageable pageable) {
//
//        return adStudentService.getStudnts(search, pageable);
//    }


}
