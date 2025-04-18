package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.LectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectInfoRepository extends JpaRepository<LectInfo, Long> {

    // findAllClass
    Page<LectInfo> findBy(Pageable pageable);

    Page<LectInfo> findByLectLocAndLectStatusAndInstNm(Pageable pageable, String sortLoc, String sortStatus, String sortInstNm);
    Page<LectInfo> findByLectLoc(Pageable pageable, String sortLoc);
    Page<LectInfo> findByLectStatus(Pageable pageable, String sortStatus);
    Page<LectInfo> findByInstNm(Pageable pageable, String sortInstNm);
    Page<LectInfo> findByLectNmContains(Pageable pageable, String findkey);

    // findOneLectListStdnt
    LectInfo findByInstNmAndLectStatus(String loginInst, String lectStatus);

    // findStudentAttendInfo
    @Query("select l.courses.fullTime from LectInfo l where l.lectNo = :lectNo")
    int findFullTimeByLectNo(int lectNo);

    Optional<LectInfo> findByLectNo(int lectNo);

}
