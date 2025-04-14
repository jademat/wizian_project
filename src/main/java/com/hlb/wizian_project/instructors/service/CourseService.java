package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.*;

import java.util.Map;

public interface CourseService {

    CourseStdntInstListDTO findOneLectListStdnt(int cpg, String loginInst);

    CourseStdntApplyListDTO findStudentListApplyInfo(int cpg, String sortStatus, String sortGender, String findkey, String loginInst);

    CourseStdntApplyListDTO findStudentListApplyInfoAttendList(int cpg, String sortStatus, String sortDate, String findkey, String loginInst);

    CourseProblemListInstDTO findProblemInfoList(int cpg, String sortYear, String sortHalf, String findkey, String loginInst);
}
