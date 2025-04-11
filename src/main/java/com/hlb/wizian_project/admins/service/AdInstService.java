package com.hlb.wizian_project.admins.service;

import com.hlb.wizian_project.admins.domain.InstDTO;
import com.hlb.wizian_project.domain.Inst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdInstService {
    Page<InstDTO> getInsts(String search, Pageable pageable);
    Optional<Inst> getInstById(int instNo);

}
