package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.AttendGradeDTO;
import com.hlb.wizian_project.domain.StudtList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudtListInstRepository extends JpaRepository<StudtList, Long> {

    // findStudentListApplyInfoAttendList
    List<StudtList> findByLectInfo_LectNo(@Param("lectInfoLectNo") int lectInfoLectNo);

    // findStudentAttendInfo
    List<StudtList> findByLectInfo_LectNoAndStudnt_StdntNo(@Param("lectNo")int lectNo, @Param("studentNo") int studentNo);

    int countByLectInfo_LectNoAndStudnt_StdntNoAndAttendStatus(@Param("lectNo") int lectNo, @Param("studentNo") int studentNo, int num);

}
