package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.AssignInfo;
import com.hlb.wizian_project.domain.AssignSubmit;
import com.hlb.wizian_project.domain.ProblemGradeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignSubmitInstRepository extends JpaRepository<AssignSubmit, Long> {

    // findGradeInfoList
    @Query("select new com.hlb.wizian_project.domain.ProblemGradeDTO(s.studnt.stdntNo, s.studnt.stdntNm, s.assignPoint, s.assignDuedate) from AssignSubmit s where s.lectInfo.lectNo = :lectNo")
    List<ProblemGradeDTO> findproblemGradeList(@Param("lectNo") int lectNo);
}
