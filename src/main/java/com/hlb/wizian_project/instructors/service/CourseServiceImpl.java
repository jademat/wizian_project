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
    private final GradesInstRepository gradesMapper;

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


    @Transactional
    @Override
    public CourseGradeListInstDTO findGradeInfoList(int cpg, String sortAttend, String sortProNm, String findkey, String findkeySub, String loginInst) {
        Pageable pageable = PageRequest.of(cpg - 1, 5, Sort.Direction.ASC, "gradesNo");
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNmAndLectStatus(loginInst, "OPEN");
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 학생 성적 리스트 출력 - 검색
        Page<Grades> GradeInfoList = null;
//        if (!sortAttend.equals("default") && !sortProNm.equals("default")) {
//            GradeInfoList = gradesMapper.findBy(sortAttend, sortProNm);
//        } else if (!sortAttend.equals("default") && sortProNm.equals("default")) {
//            GradeInfoList = gradesMapper.findByLectInfo_LectNoAndAssignInfoYear(sortAttend);
//        } else if (sortAttend.equals("default") && !sortProNm.equals("default")) {
//            GradeInfoList = gradesMapper.findByLectInfo_LectNoAndAssignInfoMonth(sortProNm);
//        }else {
//            GradeInfoList = gradesMapper.findByLectInfo_LectNo();
//        }

        GradeInfoList = gradesMapper.findByLectApply_LectInfo_LectNo(lectNo, pageable);

        // 학생 과제정보 리스트 출력
        List<ProblemGradeDTO> problemGradeList = assignSubmitMapper.findproblemGradeList(lectNo);

//        if (!findkey.equals("all") && !findkeySub.equals("all")) {
//            GradeInfoList = gradesMapper.findByLectApply_LectInfo_LectNoAndStudnt_StdntNmAnd(lectNo, findkey, findkeySub);
//        } else if (findkey.equals("all") && !findkeySub.equals("all")) {
//            GradeInfoList = gradesMapper.findByLectApply_LectInfo_LectNoAndStudnt_StdntNm(lectNo, findkey);
//        } else {
//            GradeInfoList = gradesMapper.findByLectApply_LectInfo_LectNoAnd(lectNo, findkeySub);
//        }
        List<Grades> classes = GradeInfoList.getContent();
        int totalItems = (int) GradeInfoList.getTotalElements();

        return new CourseGradeListInstDTO(cpg, totalItems, pageSize, problemGradeList, classes);
    }


    @Override
    public AttendGradeDTO findStudentAttendInfo(int studentNo, String loginInst) {
        // 강사가 진행하는 수업정보 출력
        LectInfo oneLectData = lectInfoMapper.findByInstNmAndLectStatus(loginInst, "OPEN");
        // 강사가 진행하는 강의 번호 출력
        int lectNo = oneLectData.getLectNo();
        // 강사가 진행하는 강의를 듣는 학생의 출석정보 출력
        List<StudtList> StudtOne = studtListMapper.findByLectInfo_LectNoAndStudnt_StdntNo(lectNo, studentNo);
        // 해당학생의 출석일수 계산
        int attendTime = studtListMapper.countByLectInfo_LectNoAndStudnt_StdntNoAndAttendStatus(lectNo, studentNo, 1);
        // 해당학생의 지각일수 계산
        int lateTime = studtListMapper.countByLectInfo_LectNoAndStudnt_StdntNoAndAttendStatus(lectNo, studentNo, 2);
        // 해당학생의 결석일수 계산
        int absTime = studtListMapper.countByLectInfo_LectNoAndStudnt_StdntNoAndAttendStatus(lectNo, studentNo, 0);

        AttendGradeDTO attendGrade = new AttendGradeDTO();
        attendGrade.setStdntNo(studentNo);
        attendGrade.setStdntNm(studentMapper.findStdntNmByStdntNo(studentNo));
        attendGrade.setFullTime(lectInfoMapper.findFullTimeByLectNo(lectNo));
        attendGrade.setAttendTime(attendTime);
        attendGrade.setLateTime(lateTime);
        attendGrade.setAbsTime(absTime);

        return attendGrade;
    }
}
