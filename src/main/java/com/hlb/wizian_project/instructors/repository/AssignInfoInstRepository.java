package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.AssignInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignInfoInstRepository extends JpaRepository<AssignInfo, Long> {

    // findProblemInfoList
    List<AssignInfo> findByLectInfo_LectNo(int lectNo);

    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoYearAndAssignInfoMonth(int lectNo, String sortYear, String sortHalf);
    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoYear(int lectNo, String sortYear);
    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoMonth(int lectNo, String sortHalf);
    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoNm(int lectNo, String findkey);
}
