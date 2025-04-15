package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstRepository extends JpaRepository<Inst, Long> {
    Optional<Inst> findByInstId(String instId);
}
