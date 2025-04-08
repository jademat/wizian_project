package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cour_no", nullable = false)
    private Integer courNo;

    @Column(name = "cour_nm", length = 100, nullable = false)
    private String courNm;

    @Column(name = "cour_year", length = 8, nullable = false)
    private String courYear;

    @Column(name = "cour_dept", length = 10, nullable = false)
    private String courDept;

    @Column(name = "cour_week", length = 8, nullable = false)
    private String courWeek;

    @Column(name = "week_time", nullable = false)
    private Integer weekTime;

    @Column(name = "full_time", nullable = false)
    private Integer fullTime;
}