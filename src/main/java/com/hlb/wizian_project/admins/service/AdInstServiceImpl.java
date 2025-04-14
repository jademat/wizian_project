package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.InstDTO;
import com.hlb.wizian_project.admins.repository.AdInstRepository;


import com.hlb.wizian_project.domain.Courses;
import com.hlb.wizian_project.domain.Inst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdInstServiceImpl implements AdInstService {

    private final AdInstRepository adInstRepository;

    @Override
    public Page<Inst> getInsts(String search, Pageable pageable) {
        return (search == null || search.trim().isEmpty())
                ? adInstRepository.findAllInstDtos(pageable)
                : adInstRepository.searchInsts(search, pageable);
    }

    @Override
    public Optional<Inst> getInstById(int instNo) {
        return adInstRepository.findById(instNo);
    }

    @Override
    public Inst newInst(Inst inst) {
        return adInstRepository.save(inst);
    }

}
