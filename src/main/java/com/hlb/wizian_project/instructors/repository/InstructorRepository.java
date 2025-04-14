package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository  extends JpaRepository<Inst, Integer> {

    Optional<Inst> findByInstId(String instId);



}
