package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.LectInfo;

import java.util.List;

public interface LectApplyService {

    List<LectInfo> getLectures();  // 강의 목록 조회

    void applyForLecture(String stdntId, int lectId);  // 수강신청 처리
}
