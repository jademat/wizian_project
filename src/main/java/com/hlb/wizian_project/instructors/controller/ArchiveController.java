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

@CrossOrigin(origins= {"http://localhost:3000"})
@Slf4j
@RestController
@RequestMapping("/api/inst/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;




}
