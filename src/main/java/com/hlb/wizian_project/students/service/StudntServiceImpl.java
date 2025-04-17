package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.repository.StudntRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudntServiceImpl implements StudntService {

    private final StudntRepository studntRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public Studnt newStudnt(Studnt studnt) {

        if (studntRepository.existsByStdntId(studnt.getStdntId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다!!");
        }

        if (studntRepository.existsByStdntEmail(studnt.getStdntEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다!!");
        }

        try {
            // 인증코드 생성
            String verificationCode = generateVerificationCode(studnt.getStdntEmail());
            studnt.setVerifycode(verificationCode);

            // 이메일로 인증 링크 발송
            sendVerificationEmail(studnt.getStdntEmail(), studnt.getStdntId(), verificationCode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("인증코드 발송 문제 발생!!");
        }

        studnt.setPwd(passwordEncoder.encode(studnt.getPwd()));
        return studntRepository.save(studnt);
    }

    @Override
    public String generateVerificationCode(String email) {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    // 회원가입 시 이메일 인증 링크 발송
    @Override
    public void sendVerificationEmail(String email, String userId, String verificationCode) {
        try {
            // 인증 링크 생성
            String verificationLink = "http://localhost:8080/api/auth/stdnt/verifyCode/" + userId + "/" + email + "/" + verificationCode;

            // 이메일 내용
            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='ko'>"
                    + "<head><meta charset='UTF-8'><title>WIZIAN 회원가입 이메일 인증</title></head>"
                    + "<body style='font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 30px;'>"
                    + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; padding: 40px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                    + "<h2 style='color: #4CAF50;'>🎉 WIZIAN에 오신 것을 환영합니다!</h2>"
                    + "<p style='font-size: 16px;'>안녕하세요, <strong>" + userId + "</strong>님!</p>"
                    + "<p style='font-size: 16px;'>회원가입을 완료하려면 아래 버튼을 클릭하여 이메일 인증을 진행해주세요.</p>"
                    + "<div style='text-align: center; margin: 30px 0;'>"
                    + "<a href='" + verificationLink + "' style='background-color: #4CAF50; color: white; padding: 14px 25px; text-decoration: none; border-radius: 5px; font-size: 16px;'>"
                    + "이메일 인증하기"
                    + "</a>"
                    + "</div>"
                    + "<p style='font-size: 14px; color: #888;'>만약 버튼이 작동하지 않는다면 아래 링크를 복사해서 브라우저에 붙여넣으세요:</p>"
                    + "<p style='font-size: 14px; word-break: break-all;'><a href='" + verificationLink + "' style='color: #4CAF50;'>" + verificationLink + "</a></p>"
                    + "<hr style='margin-top: 40px;'>"
                    + "<p style='font-size: 12px; color: #aaa;'>이 메일은 시스템에서 자동으로 발송된 메일입니다. 궁금한 점은 고객센터로 문의해주세요.</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 보내는 사람, 받는 사람, 제목 설정
            helper.setFrom("noreply@wizian.com");
            helper.setTo(email);
            helper.setSubject("WIZIAN 회원가입 이메일 인증");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("이메일 발송에 문제가 발생했습니다.");
        }
    }

    // 비밀번호 재설정 인증 코드 이메일 발송
    @Override
    public void sendVerificationCodeEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Wizian Academy 비밀번호 재설정 인증 코드");
        message.setText("안녕하세요.\n\n비밀번호 재설정을 위해 아래 인증 코드를 입력하세요:\n\n" + verificationCode);

        mailSender.send(message);
    }


    // 인증 코드로 비밀번호 재설정 코드
    @Override
    public boolean verifyPasswordResetCode(String code) {
        Optional<Studnt> user = studntRepository.findByVerifycode(code);

        return user.isPresent();
    }

    // 비밀번호 재설정
    @Override
    public void resetPasswordByCode(String code, String newPwd) {
        // 비밀번호 영어 + 숫자, 최소 6자 이상
        if (!newPwd.matches(".*[a-zA-Z].*") || !newPwd.matches(".*[0-9].*") || newPwd.length() < 6) {
            throw new IllegalArgumentException("비밀번호는 최소 6자 이상이어야 하며, 영어와 숫자가 포함되어야 합니다.");
        }

        // 인증 코드로 사용자 찾기
        Optional<Studnt> user = studntRepository.findByVerifycode(code);

        if (!user.isPresent()) {
            throw new IllegalArgumentException("해당 인증 코드와 일치하는 계정을 찾을 수 없습니다.");
        }

        Studnt studnt = user.get();

        // 비밀번호 변경
        studnt.setPwd(passwordEncoder.encode(newPwd));

        try {
            studntRepository.save(studnt);
        } catch (Exception e) {
            throw new IllegalStateException("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Studnt getUserById(String stdntId) {
        return studntRepository.findByStdntId(stdntId).orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
    }

    @Override
    public void updateStdntInfo(String loginId, Studnt updatedStudnt) {
        Studnt existing = studntRepository.findByStdntId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (updatedStudnt.getPwd() != null && !updatedStudnt.getPwd().isBlank()) {
            existing.setPwd(passwordEncoder.encode(updatedStudnt.getPwd()));
        }

        existing.setStdntEmail(updatedStudnt.getStdntEmail());
        existing.setPhone(updatedStudnt.getPhone());
        existing.setZipCd(updatedStudnt.getZipCd());
        existing.setAddr(updatedStudnt.getAddr());
        existing.setAddrDtl(updatedStudnt.getAddrDtl());

        studntRepository.save(existing);
    }

    @Override
    public void resetPassword(String token, String newPwd) {
        Studnt user = studntRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }

        user.setPwd(passwordEncoder.encode(newPwd));
        user.setResetToken(null);
        user.setTokenExpiry(null);

        studntRepository.save(user);
    }

    @Override
    public void sendResetPasswordEmail(String email) {
        Optional<Studnt> optionalUser = studntRepository.findAll().stream()
                .filter(user -> email.equals(user.getStdntEmail()))
                .findFirst();

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일로 등록된 계정이 없습니다.");
        }

        Studnt user = optionalUser.get();
        String token = java.util.UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setTokenExpiry(java.time.LocalDateTime.now().plusMinutes(30));
        studntRepository.save(user);

        String resetLink = "http://localhost:5173/rePwd?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[WIZIAN ACADEMI] 비밀번호 재설정 안내");
        message.setText("안녕하세요!\n\n비밀번호를 재설정하시려면 아래 링크를 클릭해 주세요:\n\n" + resetLink + "\n\n이 링크는 30분간 유효합니다.");

        mailSender.send(message);
    }

    @Override
    public Studnt loginStdnt(Studnt studnt) {
        Studnt findStudnt = studntRepository.findByStdntId(studnt.getStdntId()).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다!!")
        );

        if (!passwordEncoder.matches(studnt.getPwd(), findStudnt.getPwd())) {
            throw new IllegalStateException("아이디나 비밀번호가 일치하지 않습니다!!");
        }

        return findStudnt;
    }

    @Override
    public boolean verifyEmail(String stdntId, String stdntEmail, String code) {
        Optional<Studnt> user = studntRepository
                .findByStdntIdAndStdntEmailAndVerifycode(stdntId, stdntEmail, code);

        if (user.isPresent()) {
            user.get().setVerifycode(null);
            user.get().setEnable("true");
            studntRepository.save(user.get());
            return true;
        }
        return false;
    }

    @Override
    public Studnt findOrRegisterKakaoUser(String kakaoId, String nickname, String email) {
        return studntRepository.findByStdntId(kakaoId)
            .orElseGet(() -> {
                Studnt newUser = Studnt.builder()
                        .stdntId(kakaoId)
                        .stdntNm(nickname)
                        .stdntEmail(email)
                        .pwd("kakao")
                        .phone("01000000000")
                        .enable("true")
                        .role("STUDENT")
                        .loginType("KAKAO")
                        .build();
                return studntRepository.save(newUser);
            });
    }


    @Override
    public boolean existsByStdntId(String stdntId) {
        return studntRepository.existsByStdntId(stdntId);
    }

    @Override
    public Studnt findOrRegisterGoogleUser(String googleId, String name, String email) {
        return studntRepository.findByStdntId(googleId)
            .orElseGet(() -> {
                Studnt newUser = Studnt.builder()
                        .stdntId(googleId)
                        .stdntNm(name)
                        .stdntEmail(email)
                        .pwd("google") // 임시 비밀번호
                        .phone("01000000000")
                        .zipCd("00000")
                        .enable("true")
                        .role("STUDENT")
                        .loginType("GOOGLE")
                        .build();
                return studntRepository.save(newUser);
            });
    }

    @Override
    public Studnt loginStudent(String stdntId, String pwd) {
        Optional<Studnt> studentOptional = studntRepository.findByStdntId(stdntId);

        if (studentOptional.isEmpty() || !passwordEncoder.matches(pwd, studentOptional.get().getPwd())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        Studnt student = studentOptional.get();

        if (!"true".equals(student.getEnable())) {
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }

        return student;
    }

}


