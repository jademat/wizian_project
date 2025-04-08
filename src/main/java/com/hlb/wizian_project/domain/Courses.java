package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int courNo;

    @Column(length = 100, nullable = false)
    private String courNm;

    @Column(length = 8, nullable = false)
    private String courYear;

    @Column(length = 10, nullable = false)
    private String courDept;

    @Column(length = 8, nullable = false)
    private String courWeek;

    @Column(nullable = false)
    private int weekTime;

    @Column(nullable = false)
    private int fullTime;
}