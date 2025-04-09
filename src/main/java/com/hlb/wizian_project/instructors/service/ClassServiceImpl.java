package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.Courses;
import com.hlb.wizian_project.instructors.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classMapper;
    @Value("${inst.pagesize}")
    private int pageSize;


    @Override
    public CourseInstListDTO readClass(int cpg) {
        Pageable pageable = PageRequest.of(cpg - 1, pageSize, Sort.Direction.DESC, "courNo");

        Page<Courses> pageclasses = classMapper.findBy(pageable);
        List<Courses> classes = pageclasses.getContent();
        int totalItems = (int) pageclasses.getTotalElements();
        int cntpg = pageclasses.getTotalPages();

        return new CourseInstListDTO(cpg, totalItems, pageSize, classes);
    }


    @Override
    public CourseInstListDTO findClass(int cpg, String sortYear, String sortWeek, String findkey) {
        Pageable pageable = PageRequest.of(cpg - 1, pageSize, Sort.Direction.DESC, "courNo");
        Page<Courses> pageclasses = null;

        if (!sortYear.equals("default") && !sortWeek.equals("default")) {
            pageclasses = classMapper.findByCourYearAndCourWeek(pageable, sortYear, sortWeek);
        } else if (!sortYear.equals("default") && sortWeek.equals("default")) {
            pageclasses = classMapper.findByCourYear(pageable, sortYear);
        } else if (sortYear.equals("default") && !sortWeek.equals("default")) {
            pageclasses = classMapper.findByCourWeek(pageable, sortWeek);
        }else {
            pageclasses = classMapper.findBy(pageable);
        }
        if (!findkey.equals("all")) {
            pageclasses = classMapper.findByCourNmContains(pageable, findkey);
        }

        List<Courses> classes = pageclasses.getContent();
        int totalItems = (int) pageclasses.getTotalElements();

        return new CourseInstListDTO(cpg, totalItems, pageSize, classes);
    }
}
