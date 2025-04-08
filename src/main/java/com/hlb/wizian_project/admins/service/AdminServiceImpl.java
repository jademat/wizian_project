package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.repository.AdminRepository;
import com.hlb.wizian_project.domain.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

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
}
