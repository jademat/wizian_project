package com.hlb.wizian_project.admins.repository;

import com.hlb.wizian_project.admins.domain.InstDTO;
import com.hlb.wizian_project.domain.Inst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdInstRepository extends JpaRepository<Inst, Integer> {

    @Query("SELECT i FROM Inst i " +
            "WHERE LOWER(i.instId) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(i.instNm) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(i.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "ORDER BY i.instNo DESC")
    Page<InstDTO> searchInsts(@Param("search") String search, Pageable pageable);

    @Query("SELECT i FROM Inst i ORDER BY i.instNo DESC")
    Page<InstDTO> findAllInstDtos(Pageable pageable);


}
