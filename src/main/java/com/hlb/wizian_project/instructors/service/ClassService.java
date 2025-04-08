package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import org.springframework.stereotype.Service;

public interface ClassService {

    CourseInstListDTO readClass(int cpg);
}
