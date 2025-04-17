package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.ApplyLectDTO;
import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;

import java.util.List;

public interface LectApplyService {

    List<LectInfo> getLectures();  // 강의 목록 조회

    void applyForLecture(String stdntId, int lectId);  // 수강신청 처리

    List<LectApply>  getAppliedLecturesByStudent(String stdntId);

    List<ApplyLectDTO> getLecturesWithApplyStatus(String stdntId); // 이미 신청했는지

    void cancelLecture(String stdntId, int lectNo); // 수강신청 취소

}
