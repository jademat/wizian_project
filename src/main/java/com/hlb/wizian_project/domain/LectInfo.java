package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lect_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int lectNo;

    @Column(length = 100, nullable = false)
    private String lectNm;

    @Column(length = 50, nullable = false)
    private String instNm;

    @Column(nullable = false)
    private int lectPrice;

    @Column(length = 2000)
    private String lectDesc;

    @Column(length = 2000)
    private String lectSchd;

    @Column(nullable = false)
    private LocalDateTime lectStart;

    @Column(nullable = false)
    private LocalDateTime lectSubmit;

    @Column(nullable = false)
    private int lectPers;

    @Column(length = 200, nullable = false)
    private String lectLoc;

    @Column(length = 10, nullable = false)
    private String lectStatus;

    @Column(nullable = false)
    private int studtLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cour_no", nullable = false)
    private Courses courses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inst_no", nullable = false)
    private Inst inst;
}