package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.AssignInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AssignInfoInstRepository extends JpaRepository<AssignInfo, Long> {

    // findProblemInfoList
    List<AssignInfo> findByLectInfo_LectNo(@Param("lectNo")int lectNo);

    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoYearAndAssignInfoMonth(@Param("lectNo") int lectNo, @Param("sortYear") String sortYear, @Param("sortHalf") String sortHalf);
    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoYear(@Param("lectNo") int lectNo, @Param("sortYear") String sortYear);
    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoMonth(@Param("lectNo") int lectNo, @Param("sortHalf") String sortHalf);
    List<AssignInfo> findByLectInfo_LectNoAndAssignInfoNm(@Param("lectNo") int lectNo, @Param("findkey") String findkey);

    // archiveMyProblem
    int countByLectInfo_LectNo(@Param("lectNo") int lectNo);
}
