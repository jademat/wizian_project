package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudntRepository extends JpaRepository<Studnt, Long> {

    // findOneLectListStdnt
    @Query("select s from StudtList l join Studnt s on l.studnt.stdntNo = s.stdntNo where l.lectInfo.lectNo = :lectNo")
    List<Studnt> findLectStudentList(@Param("lectNo") int lectNo);

}
