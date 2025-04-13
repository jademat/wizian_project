package com.hlb.wizian_project.students.repository;

import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StLectApplyRepository extends JpaRepository<LectApply, Integer> {

    // 학생이 이미 신청한 특정 강의가 있는지 확인하는 메소드 (학생 번호와 강의 정보로 조회)
    Optional<LectApply> findByStudntAndLectInfo(Studnt studnt, LectInfo lectInfo);


}
