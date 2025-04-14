package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.instructors.repository.InstructorRepository;
import com.hlb.wizian_project.instructors.repository.LectApplyRepository;
import com.hlb.wizian_project.students.service.LectApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService{

    private final InstructorRepository instructorRepository;

    @Override
    public Optional<Inst> getInstructorByInstId(String instId) {
        return instructorRepository.findByInstId(instId);
    }


}
