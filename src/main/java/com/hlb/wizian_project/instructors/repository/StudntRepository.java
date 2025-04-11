package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.domain.StudntAttendListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudntRepository extends JpaRepository<Studnt, Long> {

    // CourseStdntInstListDTO
    @Query("select distinct s from StudtList l join Studnt s on l.studnt.stdntNo = s.stdntNo where l.lectInfo.lectNo = :lectNo")
    List<Studnt> findLectStudentList(@Param("lectNo") int lectNo);

    // CourseStdntInstListDTO
    @Query("select distinct new com.hlb.wizian_project.domain.StudntAttendListDTO(l.studnt.stdntNo, l.studnt.stdntId, l.studnt.stdntNm, l.studnt.stdntEmail, l.studnt.phone, l.attendDate, l.attendStatus) from StudtList l where l.lectInfo.lectNo = :lectNo and l.attendStatus = :sortStatus and l.studnt.genCd = :sortGender")
    List<StudntAttendListDTO> findStudentAttendListSearchStatusAndGender(@Param("lectNo") int lectNo, @Param("sortStatus") String sortStatus, @Param("sortGender") String sortGender);
    @Query("select distinct new com.hlb.wizian_project.domain.StudntAttendListDTO(l.studnt.stdntNo, l.studnt.stdntId, l.studnt.stdntNm, l.studnt.stdntEmail, l.studnt.phone, l.attendDate, l.attendStatus) from StudtList l where l.lectInfo.lectNo = :lectNo and l.attendStatus = :sortStatus")
    List<StudntAttendListDTO> findStudentAttendListSearchStatus(@Param("lectNo") int lectNo, @Param("sortStatus") String sortStatus);
    @Query("select distinct new com.hlb.wizian_project.domain.StudntAttendListDTO(l.studnt.stdntNo, l.studnt.stdntId, l.studnt.stdntNm, l.studnt.stdntEmail, l.studnt.phone, l.attendDate, l.attendStatus) from StudtList l where l.lectInfo.lectNo = :lectNo and l.studnt.genCd = :sortGender")
    List<StudntAttendListDTO> findStudentAttendListSearchGender(@Param("lectNo") int lectNo, @Param("sortGender") String sortGender);
    @Query("select distinct new com.hlb.wizian_project.domain.StudntAttendListDTO(l.studnt.stdntNo, l.studnt.stdntId, l.studnt.stdntNm, l.studnt.stdntEmail, l.studnt.phone, l.attendDate, l.attendStatus) from StudtList l where l.studnt.stdntNm like :findkey and l.lectInfo.lectNo = :lectNo")
    List<StudntAttendListDTO> findStudentAttendListSearchFindkey(@Param("lectNo") int lectNo, @Param("findkey") String findkey);


    @Query("select distinct new com.hlb.wizian_project.domain.StudntAttendListDTO(l.studnt.stdntNo, l.studnt.stdntId, l.studnt.stdntNm, l.studnt.stdntEmail, l.studnt.phone, l.attendDate, l.attendStatus) from StudtList l where l.lectInfo.lectNo = :lectNo")
    List<StudntAttendListDTO> findStudentAttendList(@Param("lectNo") int lectNo);

}