package com.hlb.wizian_project.students.repository;

import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudntRepository extends JpaRepository<Studnt, Long> {

    boolean existsByStdntId(String stdntId);
    boolean existsByStdntEmail(String stdntEmail);
    Optional<Studnt> findByStdntId(String stdntId);
    Optional<Studnt> findByStdntIdAndStdntEmailAndVerifycode(String stdntId, String stdntEmail, String code);
    Optional<Studnt> findByResetToken(String resetToken);
    Optional<Studnt> findByVerifycode(String code);
}
