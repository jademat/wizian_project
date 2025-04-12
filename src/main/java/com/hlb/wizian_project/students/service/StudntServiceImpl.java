package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.repository.StudntRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudntServiceImpl implements StudntService {

    private final StudntRepository studntRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public Studnt newStudnt(Studnt studnt) {
        // 아이디 중복 체크
        if (studntRepository.existsByStdntId(studnt.getStdntId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다!!");
        }

        // 이메일 중복 체크
        if (studntRepository.existsByStdntEmail(studnt.getStdntEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다!!");
        }

        try {
            // 인증코드 생성
            String verificationCode = generateVerificationCode(studnt.getStdntEmail());
            studnt.setVerifycode(verificationCode);  // 계정에 생성된 인증코드를 설정

            // 이메일로 인증 링크 발송
            sendVerificationEmail(studnt.getStdntEmail(), studnt.getStdntId(), verificationCode); // 인증 링크 발송
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("인증코드 발송 문제 발생!!");
        }

        // 비밀번호 암호화 후 저장
        studnt.setPwd(passwordEncoder.encode(studnt.getPwd()));
        return studntRepository.save(studnt);
    }

    // 6자리 인증 코드 생성
    @Override
    public String generateVerificationCode(String email) {
        // 6자리 인증 코드 생성
        return RandomStringUtils.randomAlphanumeric(6);
    }

    // 회원가입 시 이메일 인증 링크 발송
    @Override
    public void sendVerificationEmail(String email, String userId, String verificationCode) {
        // 인증 링크 생성 (회원가입 인증용 링크)
        String verificationLink = "http://localhost:8080/api/auth/stdnt/verifyCode/" + userId + "/" + email + "/" + verificationCode;

        // 인증 링크 발송을 위한 이메일 준비
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("WIZIAN ACADEMI 회원가입 이메일 인증");
        message.setText("안녕하세요^^ 가입해 주셔서 감사합니다!\n\n" +
                "아래 링크를 클릭하여 이메일 인증을 완료 하셔야 로그인 가능합니다.\n\n" +
                verificationLink); // 인증 링크 포함

        mailSender.send(message);
    }

    // 비밀번호 재설정을 위한 인증 코드 이메일 발송
    @Override
    public void sendVerificationCodeEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Wizian Academy 비밀번호 재설정 인증 코드");
        message.setText("안녕하세요.\n\n비밀번호 재설정을 위해 아래 인증 코드를 입력하세요:\n\n" + verificationCode);

        mailSender.send(message);  // 이메일 발송
    }


    // 인증 코드로 비밀번호 재설정 코드 검증
    @Override
    public boolean verifyPasswordResetCode(String code) {
        Optional<Studnt> user = studntRepository.findByVerifycode(code);

        return user.isPresent();
    }

    // 비밀번호 재설정
    @Override
    public void resetPasswordByCode(String code, String newPwd) {
        // 비밀번호 복잡도 체크 (영어 + 숫자, 최소 6자 이상)
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
        studnt.setPwd(passwordEncoder.encode(newPwd));  // 새로운 비밀번호 암호화 후 설정

        try {
            studntRepository.save(studnt);  // 새 비밀번호 저장
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

        // 변경 가능한 항목만 업데이트
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

        user.setPwd(passwordEncoder.encode(newPwd)); // 비밀번호 암호화 후 저장
        user.setResetToken(null);                    // 토큰 제거 (재사용 방지)
        user.setTokenExpiry(null);                   // 만료일 제거

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
            user.get().setVerifycode(null); // 인증코드 초기화
            user.get().setEnable("true");  // 로그인 가능하도록 설정
            studntRepository.save(user.get()); // 변경된 내용을 레포지토리로 넘김(db 바뀌게)
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
        return studntRepository.existsByStdntId(stdntId);  // 아이디 중복 체크
    }
}


