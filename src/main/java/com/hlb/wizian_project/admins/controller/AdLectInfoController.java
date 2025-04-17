package com.hlb.wizian_project.admins.controller;


import com.hlb.wizian_project.admins.domain.*;
import com.hlb.wizian_project.admins.service.AdLectInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins/lect")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequiredArgsConstructor
@Slf4j
public class AdLectInfoController {

    private final AdLectInfoService adLectService;


    @GetMapping("/lectlist")
    public Page<LectListDTO> getLects(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "lectNo", direction = Sort.Direction.DESC) Pageable pageable) {
        return adLectService.getLects(search, pageable);
    }


    @GetMapping("/detail/{lectNo}")
    public ResponseEntity<?> getLect(@PathVariable int lectNo) {
        Optional<LectDetailDTO> lect = adLectService.getLectById(lectNo);
        return lect.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerLecture(@RequestBody LectDTO dto) {
        try {
            adLectService.lectRegister(dto);
            return ResponseEntity.ok().body("강의 등록 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("강의 등록 실패: " + e.getMessage());
        }
    }

    @GetMapping("/check-name")
    public ResponseEntity<Boolean> checkLectureName(@RequestParam String name) {
        boolean exists = adLectService.isLectureNameDuplicate(name);
        return ResponseEntity.ok(!exists);  // 사용 가능하면 true
    }

    @GetMapping("/assigned-inst")
    public ResponseEntity<List<InstNoDTO>> getAssignedInstructors() {
        List<InstNoDTO> instructors = adLectService.getAssignedInstructors();
        return ResponseEntity.ok(instructors);
    }


}
