package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.*;

public interface ArchiveService {

    MyProblemListInstDTO archiveMyProblem(int cpg, String sortYear, String sortHalf, String findkey, String loginInst);
}
