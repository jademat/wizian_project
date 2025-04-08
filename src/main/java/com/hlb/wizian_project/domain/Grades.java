package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    @Column(name = "apply_no", nullable = false)
    private Integer applyNo;

    @Column(name = "stdnt_no", nullable = false)
    private Integer stdntNo;

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