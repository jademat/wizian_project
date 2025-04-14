package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.*;
import com.hlb.wizian_project.instructors.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final LectInfoRepository lectInfoMapper;
    private final StudntInstRepository studentMapper;
    private final LectApplyRepository lectApplyMapper;
    private final StudtListInstRepository studtListMapper;
    private final AssignInfoInstRepository assignInfoMapper;
    private final AssignSubmitInstRepository assignSubmitMapper;
    @Value("${inst.pagesize}")
    private int pageSize;


    @Transactional
    @Override
    public CourseStdntInstListDTO findOneLectListStdnt(int cpg, String loginInst) {
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNmAndLectStatus(loginInst, "OPEN");
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 강사가 진행하는 수업을 듣는 학생리스트 출력
        List<Studnt> students = studentMapper.findLectStudentList(lectNo);
        // 학생리스트 카운트
        int totalItems = students.size();

        return new CourseStdntInstListDTO(cpg, totalItems, pageSize, students, oneLectData);
    }


    @Transactional
    @Override
    public CourseStdntApplyListDTO findStudentListApplyInfo(int cpg, String sortStatus, String sortGender, String findkey, String loginInst) {
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNmAndLectStatus(loginInst, "OPEN");
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 강사가 진행하는 수업을 듣는 학생, 출결 리스트 출력 - 검색
        List<StudntAttendListDTO> studentAttendList = null;
        if (!sortStatus.equals("default") && !sortGender.equals("default")) {
            studentAttendList = studentMapper.findStudentAttendListSearchStatusAndGender(lectNo, sortStatus, sortGender);
        } else if (!sortStatus.equals("default") && sortGender.equals("default")) {
            studentAttendList = studentMapper.findStudentAttendListSearchStatus(lectNo, sortStatus);
        } else if (sortStatus.equals("default") && !sortGender.equals("default")) {
            studentAttendList = studentMapper.findStudentAttendListSearchGender(lectNo, sortGender);
        }else {
            studentAttendList = studentMapper.findStudentAttendList(lectNo);
        }
        if (!findkey.equals("all")) {
            studentAttendList = studentMapper.findStudentAttendListSearchFindkey(lectNo, findkey);
        }
        // 학생, 출결 리스트 카운트
        int totalItems = studentAttendList.size();

        Map<String, List<?>> applyMap = new HashMap<>();
        // 학생리스트 출력
        applyMap.put("students", studentMapper.findAll());
        // 수강신청 리스트 출력
        applyMap.put("applys", lectApplyMapper.findByApplyStatus("APPROVED"));


        return new CourseStdntApplyListDTO(cpg, totalItems, pageSize, studentAttendList, applyMap);
    }


    @Transactional
    @Override
    public CourseStdntApplyListDTO findStudentListApplyInfoAttendList(int cpg, String sortStatus, String sortDate, String findkey, String loginInst) {
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNmAndLectStatus(loginInst, "OPEN");
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 강사가 진행하는 수업을 듣는 학생, 출결 리스트 출력 - 검색
        List<StudntAttendListDTO> studentAttendList = null;
        if (!sortStatus.equals("default") && !sortDate.equals("default")) {
            studentAttendList = studentMapper.findStudentAttendListSearchStatusAndGender(lectNo, sortStatus, sortDate);
        } else if (!sortStatus.equals("default") && sortDate.equals("default")) {
            studentAttendList = studentMapper.findStudentAttendListSearchStatus(lectNo, sortStatus);
        } else if (sortStatus.equals("default") && !sortDate.equals("default")) {
            studentAttendList = studentMapper.findStudentAttendListSearchGender(lectNo, sortDate);
        }else {
            studentAttendList = studentMapper.findStudentAttendList(lectNo);
        }
        if (!findkey.equals("all")) {
            studentAttendList = studentMapper.findStudentAttendListSearchFindkey(lectNo, findkey);
        }
        // 학생, 출결 리스트 카운트
        int totalItems = studentAttendList.size();

        Map<String, List<?>> applyMap = new HashMap<>();
        // 학생리스트 출력
        applyMap.put("students", studentMapper.findAll());
        // 출결리스트 출력
        applyMap.put("applys", studtListMapper.findByLectInfo_LectNo(lectNo));


        return new CourseStdntApplyListDTO(cpg, totalItems, pageSize, studentAttendList, applyMap);
    }


    @Transactional
    @Override
    public CourseProblemListInstDTO findProblemInfoList(int cpg, String sortYear, String sortHalf, String findkey, String loginInst) {
        Pageable pageable = PageRequest.of(cpg - 1, 5, Sort.Direction.ASC, "assignInfo.assignQnum");
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNmAndLectStatus(loginInst, "OPEN");
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 강사가 부여한 과제 리스트 출력 - 검색
        List<AssignInfo> ProblemInfoList = null;
        if (!sortYear.equals("default") && !sortHalf.equals("default")) {
            ProblemInfoList = assignInfoMapper.findByLectInfo_LectNoAndAssignInfoYearAndAssignInfoMonth(lectNo, sortYear, sortHalf);
        } else if (!sortYear.equals("default") && sortHalf.equals("default")) {
            ProblemInfoList = assignInfoMapper.findByLectInfo_LectNoAndAssignInfoYear(lectNo, sortYear);
        } else if (sortYear.equals("default") && !sortHalf.equals("default")) {
            ProblemInfoList = assignInfoMapper.findByLectInfo_LectNoAndAssignInfoMonth(lectNo, sortHalf);
        }else {
            ProblemInfoList = assignInfoMapper.findByLectInfo_LectNo(lectNo);
        }
        if (!findkey.equals("all")) {
            ProblemInfoList = assignInfoMapper.findByLectInfo_LectNoAndAssignInfoNm(lectNo, findkey);
        }

        // 제출된 과제 리스트
        Page<AssignSubmit> assignSubmitList = assignSubmitMapper.findAll(pageable);

        List<AssignSubmit> classes = assignSubmitList.getContent();
        int totalItems = (int) assignSubmitList.getTotalElements();


        return new CourseProblemListInstDTO(cpg, totalItems, pageSize, ProblemInfoList, classes);
    }
}
