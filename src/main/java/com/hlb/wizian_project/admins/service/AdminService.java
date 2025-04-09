package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminService {
    Page<Admin> getAdmins(String search, Pageable pageable);
    Optional<Admin> getAdminById(int adminNo);

    Admin newAdmin(Admin admin);
}