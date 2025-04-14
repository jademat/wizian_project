package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.*;
import com.hlb.wizian_project.instructors.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {


    @Value("${inst.pagesize}")
    private int pageSize;



}
