package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.Inst;
import com.hlb.wizian_project.instructors.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService{

    private final InstructorRepository instructorRepository;

    @Override
    public Optional<Inst> getInstructorByInstId(String instId) {
        return instructorRepository.findByInstId(instId);
    }


}
