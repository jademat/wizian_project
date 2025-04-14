package com.hlb.wizian_project.instructors.repository;

import com.hlb.wizian_project.domain.AssignInfo;
import com.hlb.wizian_project.domain.AssignSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignSubmitInstRepository extends JpaRepository<AssignSubmit, Long> {

}
