package com.hlb.wizian_project.admins.controller;

import com.hlb.wizian_project.admins.service.AdminService;
import com.hlb.wizian_project.domain.Admin;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admins/admin")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://127.0.0.1:3000",
})
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/list")
    public Page<Admin> getAdmins(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        return adminService.getAdmins(search, pageable);
    }
    @GetMapping("/detail/{adminNo}")
    public ResponseEntity<Admin> getAdmin(@PathVariable int adminNo) {
        Optional<Admin> admin = adminService.getAdminById(adminNo);
        return admin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}