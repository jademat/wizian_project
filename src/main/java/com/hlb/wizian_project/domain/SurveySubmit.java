package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "survey_submit")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveySubmit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_submit_nm", nullable = false)
    private Integer surveySubmitNm;

    @Column(name = "stdnt_no", nullable = false)
    private Integer stdntNo;

    @Column(name = "survey_no", nullable = false)
    private Integer surveyNo;

    @Column(name = "survey_point", nullable = false)
    private Integer surveyPoint;
}