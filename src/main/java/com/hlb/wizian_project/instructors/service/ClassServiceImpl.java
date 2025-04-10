package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.domain.Courses;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.LectInfoInstListDTO;
import com.hlb.wizian_project.instructors.repository.CourseRepository;
import com.hlb.wizian_project.instructors.repository.LectInfoRepository;
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

    private final CourseRepository courseMapper;
    private final LectInfoRepository lectInfoMapper;
    @Value("${inst.pagesize}")
    private int pageSize;


    @Override
    public CourseInstListDTO findAllCourse(int cpg, String sortYear, String sortWeek, String findkey) {
        Pageable pageable = PageRequest.of(cpg - 1, pageSize, Sort.Direction.DESC, "courNo");
        Page<Courses> pageclasses = null;

        if (!sortYear.equals("default") && !sortWeek.equals("default")) {
            pageclasses = courseMapper.findByCourYearAndCourWeek(pageable, sortYear, sortWeek);
        } else if (!sortYear.equals("default") && sortWeek.equals("default")) {
            pageclasses = courseMapper.findByCourYear(pageable, sortYear);
        } else if (sortYear.equals("default") && !sortWeek.equals("default")) {
            pageclasses = courseMapper.findByCourWeek(pageable, sortWeek);
        }else {
            pageclasses = courseMapper.findBy(pageable);
        }
        if (!findkey.equals("all")) {
            pageclasses = courseMapper.findByCourNmContains(pageable, findkey);
        }

        List<Courses> classes = pageclasses.getContent();
        int totalItems = (int) pageclasses.getTotalElements();

        return new CourseInstListDTO(cpg, totalItems, pageSize, classes);
    }


    @Override
    public LectInfoInstListDTO findAllClass(int cpg, String sortLoc, String sortStatus, String sortInstNm, String findkey) {
        Pageable pageable = PageRequest.of(cpg - 1, pageSize, Sort.Direction.DESC, "lectNo");
        Page<LectInfo> pageclasses = null;

        if (!sortLoc.equals("default") && !sortStatus.equals("default") && !sortInstNm.equals("default")) {
            pageclasses = lectInfoMapper.findByLectLocAndLectStatusAndInstNm(pageable, sortLoc, sortStatus, sortInstNm);
        } else if (!sortLoc.equals("default") && sortStatus.equals("default") && sortInstNm.equals("default")) {
            pageclasses = lectInfoMapper.findByLectLoc(pageable, sortLoc);
        } else if (sortLoc.equals("default") && !sortStatus.equals("default") && sortInstNm.equals("default")) {
            pageclasses = lectInfoMapper.findByLectStatus(pageable, sortStatus);
        } else if (sortLoc.equals("default") && sortStatus.equals("default") && !sortInstNm.equals("default")) {
            pageclasses = lectInfoMapper.findByInstNm(pageable, sortInstNm);
        } else {
            pageclasses = lectInfoMapper.findBy(pageable);
        }
        if (!findkey.equals("all")) {
            pageclasses = lectInfoMapper.findByLectNmContains(pageable, findkey);
        }

        List<LectInfo> classes = pageclasses.getContent();
        int totalItems = (int) pageclasses.getTotalElements();

        return new LectInfoInstListDTO(cpg, totalItems, pageSize, classes);
    }
}
