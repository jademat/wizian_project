package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectApplyRepository extends JpaRepository<LectApply, Long> {

    List<LectApply> findByApplyStatus(String applyStatus);

    // archiveMyProblemCountSubmit
    long countByLectInfo_LectNoAndApplyStatus(int lectInfoLectNo, String applyStatus);
}
