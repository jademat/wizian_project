package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.Studnt;

public interface StudntService {

    Studnt newStudnt(Studnt studnt);

    Studnt loginStdnt(Studnt studnt);

    boolean verifyEmail(String stdntId, String stdntEmail, String code);

    boolean existsByStdntId(String stdntId);

    void sendVerificationEmail(String email, String userId, String verificationCode);

    boolean verifyPasswordResetCode(String code);

    Studnt getUserById(String stdntId);

    void updateStdntInfo(String loginId, Studnt updatedStudnt);

    void resetPassword(String token, String newPwd);

    void resetPasswordByCode(String code, String newPwd);

    void sendResetPasswordEmail(String email);

    String generateVerificationCode(String email);

    void sendVerificationCodeEmail(String email, String verificationCode);
}
