package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.repository.AdminRepository;
import com.hlb.wizian_project.domain.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<Admin> getAdmins(String search, Pageable pageable) {
        if (search == null || search.trim().isEmpty()) {
            return adminRepository.findAll(pageable);
        }
        return adminRepository.searchAdmins(search, pageable);
    }

    @Override
    public Optional<Admin> getAdminById(int adminNo) {
        return adminRepository.findById(adminNo);
    }

    @Override
    public Admin newAdmin(Admin admin) {
        // 아이디 중복 체크
        if (adminRepository.existsByLoginId(admin.getLoginId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다!!");
        }
        // 이메일 중복 체크
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다!!");
        }
        admin.setPswd(passwordEncoder.encode(admin.getPswd()));       // 비밀번호를 암호화시켜 저장
        return adminRepository.save(admin);
    }
}
