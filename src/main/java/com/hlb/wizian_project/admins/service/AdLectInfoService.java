package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdLectInfoService {

    int lectRegister(LectDTO dto);

    Page<LectListDTO> getLects(String search, Pageable pageable);

    Optional<LectDetailDTO> getLectById(int lectNo);

    boolean isLectureNameDuplicate(String lectNm);

    List<InstNoDTO> getAssignedInstructors();
}
