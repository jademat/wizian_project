package com.hlb.wizian_project.students.repository;

import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StLectApplyRepository extends JpaRepository<LectApply, Integer> {


    @Query("select li from LectApply li")
    List<LectApply> findByLectNo(int lectNo);

    List<LectApply> findByStudnt(Studnt studnt);

    // 학생이 이미 신청한 특정 강의가 있는지 확인하는 메소드
    boolean existsByStudntAndLectInfo(Studnt studnt, LectInfo lectInfo);

    Optional<LectApply> findByStudntAndLectInfo(Studnt studnt, LectInfo lectInfo);

    // 수강 취소 메서드
    void deleteByStudntAndLectInfo(Studnt studnt, LectInfo lectInfo);
}
