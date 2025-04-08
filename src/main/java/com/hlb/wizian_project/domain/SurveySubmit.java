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
    @Column(nullable = false)
    private int surveySubmitNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_no", nullable = false)
    private Survey survey;

    @Column(nullable = false)
    private int surveyPoint;
}