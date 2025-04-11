package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectApplyRepository extends JpaRepository<LectApply, Long> {

    // CourseStdntApplyListDTO
    List<LectApply> findByApplyStatus(String applyStatus);
}
