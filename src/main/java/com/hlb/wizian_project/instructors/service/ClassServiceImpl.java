package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstDTO;
import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.Courses;
import com.hlb.wizian_project.instructors.controller.ClassController;
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
}
