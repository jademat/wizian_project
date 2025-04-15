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
public class ArchiveServiceImpl implements ArchiveService {

    private final LectInfoRepository lectInfoMapper;
    private final AssignInfoInstRepository assignInfoMapper;
    private final AssignSubmitInstRepository assignSubmitMapper;
    private final LectApplyRepository lectApplyMapper;

    @Value("${inst.pagesize}")
    private int pageSize;


    @Transactional
    @Override
    public MyProblemListInstDTO archiveMyProblem(int cpg, String sortYear, String sortHalf, String findkey, String loginInst) {
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
        int totalItems = assignInfoMapper.countByLectInfo_LectNo(lectNo);

        return new MyProblemListInstDTO(cpg, totalItems, pageSize, ProblemInfoList);
    }


    @Override
    public Map<String, Long> archiveMyProblemCountSubmit(int infoNo, String infoNm, String loginInst) {
        // 해당강의를 듣는 총 학생 수
        Long totalStudent = lectApplyMapper.countByLectInfo_LectNoAndApplyStatus(infoNo, "APPROVED");
        // 해당 과제를 제출한 인원수 출력
        Long countSubmit = assignSubmitMapper.countByAssignInfo_AssignInfoNoAndAssignInfo_AssignInfoNm(infoNo, infoNm);

        Map<String, Long> params = new HashMap<>();
        params.put("totalStudent", totalStudent);
        params.put("countSubmit", countSubmit);

        return params;
    }
}
