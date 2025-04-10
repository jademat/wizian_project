package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.LectInfoInstListDTO;
import org.springframework.stereotype.Service;

public interface ClassService {

    CourseInstListDTO findAllCourse(int cpg, String sortYear, String sortWeek, String findkey);

    LectInfoInstListDTO findAllClass(int cpg, String sortLoc, String sortStatus, String sortInstNm, String findkey);
}
