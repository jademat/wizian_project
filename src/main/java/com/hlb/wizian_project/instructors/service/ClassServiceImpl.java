package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.CourseInstListDTO;
import com.hlb.wizian_project.instructors.controller.ClassController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final BoardRepository boardMapper;
    private final ReplyRepository replyMapper;
    @Value("${inst.pagesize}")
    private int pageSize;


    @Override
    public CourseInstListDTO readClass(int cpg) {
        return null;
    }
}
