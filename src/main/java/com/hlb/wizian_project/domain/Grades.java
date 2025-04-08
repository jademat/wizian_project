package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "grades")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grades_no", nullable = false)
    private Integer gradesNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_no", nullable = false)
    private LectApply apply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;

    @Column(name = "attend_point")
    private Integer attendPoint;

    @Column(name = "attend_onepoint")
    private Integer attendOnepoint;

    @Column(name = "assign_twopoint")
    private Integer assignTwopoint;

    @Column(name = "grades_point")
    private Integer gradesPoint;

    @Column(name = "grades_duedate", length = 8)
    private String gradesDuedate;

    @Column(name = "grades_option")
    private Integer gradesOption;
}