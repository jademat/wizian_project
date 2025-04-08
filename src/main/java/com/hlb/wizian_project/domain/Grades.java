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
    @Column(nullable = false)
    private int gradesNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_no", nullable = false, unique = true)
    private LectApply lectApply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;

    @Column
    private int attendPoint;

    @Column
    private int attendOnepoint;

    @Column
    private int assignTwopoint;

    @Column
    private int gradesPoint;

    @Column(length = 8)
    private String gradesDuedate;

    @Column
    private int gradesOption;
}