package com.hlb.wizian_project.admins.controller;

import com.hlb.wizian_project.admins.domain.InstDTO;
import com.hlb.wizian_project.admins.service.AdInstService;
import com.hlb.wizian_project.domain.Inst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins/inst")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequiredArgsConstructor
@Slf4j
public class AdInstController {
    private final AdInstService adInstService;

    @GetMapping("/list")
    public Page<Inst> getInsts(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "instNo", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Inst> result = adInstService.getInsts(search, pageable);
        if (result.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return result;
    }
    @GetMapping("/detail/{instNo}")
    public ResponseEntity<?> getInst(@PathVariable int instNo) {
        Optional<?> inst = adInstService.getInstById(instNo);
        return inst.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
