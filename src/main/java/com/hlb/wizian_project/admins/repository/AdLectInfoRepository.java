package com.hlb.wizian_project.admins.repository;

import com.hlb.wizian_project.admins.domain.InstDTO;
import com.hlb.wizian_project.admins.domain.InstNoDTO;
import com.hlb.wizian_project.admins.domain.LectDetailDTO;
import com.hlb.wizian_project.admins.domain.LectListDTO;
import com.hlb.wizian_project.domain.LectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdLectInfoRepository extends JpaRepository<LectInfo, Integer> {

    @Query("SELECT new com.hlb.wizian_project.admins.domain.LectListDTO(" +
            "l.lectNo, l.lectNm, i.instNm, l.lectStart, l.lectSubmit, l.lectStatus) " +
            "FROM LectInfo l JOIN l.inst i ORDER BY l.lectNo DESC")
    Page<LectListDTO> findAllLectDTOs(Pageable pageable);

    @Query("SELECT new com.hlb.wizian_project.admins.domain.LectListDTO(" +
            "l.lectNo, l.lectNm, i.instNm, l.lectStart, l.lectSubmit, l.lectStatus) " +
            "FROM LectInfo l " +
            "JOIN l.inst i " +
            "WHERE LOWER(l.lectNm) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(i.instNm) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "OR LOWER(l.lectStatus) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "order by l.lectNo DESC")
    Page<LectListDTO> searchLects(@Param("search") String search, Pageable pageable);


    @Query("SELECT new com.hlb.wizian_project.admins.domain.LectDetailDTO(" +
            "l.lectNo, l.lectNm, l.lectDesc, l.lectStart, l.lectSubmit, l.lectPrice, " +
            "l.lectLoc, l.lectStatus, l.studtLimit, i.instNm, c.courNm, " +
            "c.weekTime, c.fullTime) " +
            "FROM LectInfo l JOIN l.inst i JOIN l.courses c WHERE l.lectNo = :lectNo")
    Optional<LectDetailDTO> findLectDetailById(@Param("lectNo") int lectNo);

    @Query("SELECT COUNT(l) > 0 FROM LectInfo l WHERE l.lectNm = :lectNm")
    boolean existsByLectNm(@Param("lectNm") String lectNm);

    @Query("SELECT DISTINCT i.instNo AS instNo, i.instNm AS instNm " +
            "FROM LectInfo l JOIN l.inst i")
    List<InstNoDTO> findAssignedInstructors();
}
