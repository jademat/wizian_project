package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.Inst;

import java.util.Optional;

public interface InstructorService {

    Optional<Inst> getInstructorByInstId(String instId);
}
