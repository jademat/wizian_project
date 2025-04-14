package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.*;
import com.hlb.wizian_project.admins.repository.AdCourseRepository;
import com.hlb.wizian_project.admins.repository.AdInstRepository;
import com.hlb.wizian_project.admins.repository.AdLectInfoRepository;
import com.hlb.wizian_project.domain.Courses;
import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.domain.LectInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdLectInfoServiceImpl implements AdLectInfoService {

    private final AdLectInfoRepository lectInfoRepository;
    private final AdCourseRepository coursesRepository;
    private final AdInstRepository instRepository;

    @Override
    public int lectRegister(LectDTO dto) {
        LectInfo lectInfo = new LectInfo();
        lectInfo.setLectNm(dto.getLectNm());
        lectInfo.setLectDesc(dto.getLectDesc());
        lectInfo.setLectPrice(dto.getLectPrice());
        lectInfo.setLectLoc(dto.getLectLoc());
        lectInfo.setLectStatus("모집중");
        lectInfo.setStudtLimit(dto.getStudtLimit());
        lectInfo.setLectStart(dto.getLectStart().atStartOfDay());
        lectInfo.setLectSubmit(dto.getLectSubmit().atStartOfDay());

        Courses course = coursesRepository.getReferenceById(dto.getCourNo());
        Inst inst = instRepository.getReferenceById(dto.getInstNo());

        lectInfo.setCourses(course);
        lectInfo.setInst(inst);
        lectInfo.setInstNm(inst.getInstNm());

        return lectInfoRepository.save(lectInfo).getLectNo();
    }

    @Override
    public Page<LectListDTO> getLects(String search, Pageable pageable) {
        return (search == null || search.trim().isEmpty())
                ? lectInfoRepository.findAllLectDTOs(pageable)
                : lectInfoRepository.searchLects(search, pageable);
    }

    @Override
    public Optional<LectDetailDTO> getLectById(int lectNo) {
        return lectInfoRepository.findLectDetailById(lectNo);
    }

    @Override
    public boolean isLectureNameDuplicate(String lectNm) {
        return lectInfoRepository.existsByLectNm(lectNm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstNoDTO> getAssignedInstructors() {
        return lectInfoRepository.findAssignedInstructors();
    }
}
