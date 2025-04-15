package com.hlb.wizian_project.instructors.service;

import com.hlb.wizian_project.domain.*;

import java.util.Map;

public interface ArchiveService {

    MyProblemListInstDTO archiveMyProblem(int cpg, String sortYear, String sortHalf, String findkey, String loginInst);

    Map<String, Long> archiveMyProblemCountSubmit(int infoNo, String infoNm, String loginInst);
}
