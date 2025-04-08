package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lect_apply")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_no", nullable = false)
    private Integer applyNo;

    @Column(name = "stdnt_no", nullable = false)
    private Integer stdntNo;

    @Column(name = "apply_status", length = 50)
    private String applyStatus;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    @Column(name = "apply_endate")
    private LocalDateTime applyEndate;

    @Column(name = "lect_no", nullable = false)
    private Integer lectNo;
}