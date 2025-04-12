package com.hlb.wizian_project.domain;

import lombok.Data;
import org.springframework.context.annotation.Profile;

// 카카오 사용자 정보를 위한 DTO
@Data
public class KakaoUserInfo {
    private Long id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public static class Properties {
        private String nickname;
    }
    @Data
    public static class KakaoAccount {
        private String email;

    }
}
