package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.*;
import com.hlb.wizian_project.instructors.repository.CourseRepository;
import com.hlb.wizian_project.instructors.repository.LectInfoRepository;
import com.hlb.wizian_project.instructors.repository.StudntRepository;
import com.hlb.wizian_project.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final LectInfoRepository lectInfoMapper;
    private final StudntRepository studentMapper;
    @Value("${inst.pagesize}")
    private int pageSize;


    @Override
    public CourseStdntInstListDTO findOneLectListStdnt(int cpg, String loginInst) {
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNm(loginInst);
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 강사가 진행하는 수업을 듣는 학생리스트 출력
        List<Studnt> students = studentMapper.findLectStudentList(lectNo);
        // 학생리스트 카운트
        int totalItems = students.size();

        return new CourseStdntInstListDTO(cpg, totalItems, pageSize, students, oneLectData);
    }
}
