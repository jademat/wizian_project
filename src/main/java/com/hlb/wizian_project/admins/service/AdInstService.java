package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.InstLectureDTO;
import com.hlb.wizian_project.domain.Inst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdInstService {
    Page<Inst> getInsts(String search, Pageable pageable);
    Optional<Inst> getInstById(int instNo);
    Inst newInst(Inst inst);
    List<InstLectureDTO> getLecturesByInstructor(int instNo);
}
