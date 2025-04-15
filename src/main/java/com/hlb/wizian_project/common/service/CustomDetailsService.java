package com.hlb.wizian_project.common.service;

import com.hlb.wizian_project.admins.repository.AdminRepository;
import com.hlb.wizian_project.domain.Admin;
import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.instructors.repository.InstRepository;
import com.hlb.wizian_project.students.repository.StudntRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service("customDetailsService")
public class CustomDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final StudntRepository studntRepository;
    private final InstRepository instRepository;

    public CustomDetailsService(AdminRepository adminRepository, StudntRepository studntRepository, InstRepository instRepository) {
        this.adminRepository = adminRepository;
        this.studntRepository = studntRepository;
        this.instRepository = instRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 시도: {}", username);

        // 사용자 구분을 위한 로직
        // 1. 관리자 확인
        Optional<Admin> adminOpt = adminRepository.findByLoginId(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            log.info("관리자 로그인 확인: {}", username);
            return new org.springframework.security.core.userdetails.User(
                    admin.getLoginId(),
                    admin.getPswd(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // 2. 학생 확인
        Optional<Studnt> studentOpt = studntRepository.findByStdntId(username);
        if (studentOpt.isPresent()) {
            Studnt student = studentOpt.get();
            log.info("학생 로그인 확인: {}", username);
            return new org.springframework.security.core.userdetails.User(
                    student.getStdntId(),
                    student.getPwd(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        // 3. 강사 확인
        Optional<Inst> instOpt = instRepository.findByInstId(username);
        if (instOpt.isPresent()) {
            Inst inst = instOpt.get();
            log.info("강사 로그인 확인: {}", username);
            return new org.springframework.security.core.userdetails.User(
                    inst.getInstId(),
                    inst.getPasswd(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_INST"))
            );
        }

        // 관리자, 학생, 강사 모두 존재하지 않으면 예외 처리
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
}
