package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.StudtList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudtListInstRepository extends JpaRepository<StudtList, Long> {

    // findStudentListApplyInfoAttendList
    List<StudtList> findByLectInfo_LectNo(int lectInfoLectNo);

}
