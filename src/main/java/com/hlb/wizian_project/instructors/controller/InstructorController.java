package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.instructors.repository.InstructorRepository;
import com.hlb.wizian_project.instructors.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping("/{instId}")
    public ResponseEntity<?> getInstructorDetail(@PathVariable String instId) {
        Optional<Inst> instructor = instructorService.getInstructorByInstId(instId);

        if (instructor.isPresent()) {
            return ResponseEntity.ok(instructor.get());
        } else {
            log.error("강사 정보를 찾을 수 없습니다: instId={}", instId);
            // JSON 형식으로 에러 메시지를 보냄
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "강사 정보를 찾을 수 없습니다."));
        }
    }

    
}
