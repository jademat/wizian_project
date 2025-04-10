package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.LectInfoInstListDTO;

public interface CourseService {

    CourseInstListDTO findAllCourse(int cpg, String sortYear, String sortWeek, String findkey);

    LectInfoInstListDTO findAllClass(int cpg, String sortLoc, String sortStatus, String sortInstNm, String findkey);
}
