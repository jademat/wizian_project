package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.CourseStdntApplyListDTO;
import com.hlb.wizian_project.domain.CourseStdntInstListDTO;
import com.hlb.wizian_project.domain.LectInfoInstListDTO;

import java.util.Map;

public interface CourseService {

    CourseStdntInstListDTO findOneLectListStdnt(int cpg, String loginInst);

    CourseStdntApplyListDTO findStudentListApplyInfo(int cpg, String sortStatus, String sortGender, String findkey, String loginInst);
}
