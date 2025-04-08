package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_no", nullable = false)
    private Integer surveyNo;

    @Column(name = "survey_title", length = 1000, nullable = false)
    private String surveyTitle;

    @Column(name = "survey_qnum", length = 1000, nullable = false)
    private String surveyQnum;

    @Column(name = "survey_date", nullable = false)
    private LocalDateTime surveyDate;

    @Column(name = "survey_year", length = 8, nullable = false)
    private String surveyYear;

    @Column(name = "survey_type", length = 1000, nullable = false)
    private String surveyType;
}