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
    @Column(nullable = false)
    private int surveyNo;

    @Column(length = 100, nullable = false)
    private String surveyTitle;

    @Column(length = 10, nullable = false)
    private String surveyQnum;

    @Column(nullable = false)
    private LocalDateTime surveyDate;

    @Column(length = 10, nullable = false)
    private String surveyYear;

    @Column(length = 20, nullable = false)
    private String surveyType;
}