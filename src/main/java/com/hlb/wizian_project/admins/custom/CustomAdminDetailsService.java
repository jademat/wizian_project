package com.hlb.wizian_project.admins.custom;

import com.hlb.wizian_project.admins.repository.AdminRepository;
import com.hlb.wizian_project.domain.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    // JPA를 이용해서 사용자 정보를 데이터베이스에서 조회하고 그 결과를 UserDetails에 저장하고 반환
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        log.info("loadUserByUsername 호출 : {}",loginId);


        Admin admin = adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getLoginId())
                .password(admin.getPswd())
                .roles(admin.getRole())
                .build();
    }
}
