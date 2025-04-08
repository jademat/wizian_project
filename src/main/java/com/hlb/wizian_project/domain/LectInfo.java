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
    @Column(name = "lect_no", nullable = false)
    private Integer lectNo;

    @Column(name = "lect_nm", length = 100, nullable = false)
    private String lectNm;

    @Column(name = "inst_nm", length = 50, nullable = false)
    private String instNm;

    @Column(name = "lect_price", nullable = false)
    private Integer lectPrice;

    @Column(name = "lect_desc", length = 2000)
    private String lectDesc;

    @Column(name = "lect_schd", length = 2000)
    private String lectSchd;

    @Column(name = "lect_start", nullable = false)
    private LocalDateTime lectStart;

    @Column(name = "lect_submit", nullable = false)
    private LocalDateTime lectSubmit;

    @Column(name = "lect_pers", nullable = false)
    private Integer lectPers;

    @Column(name = "lect_loc", length = 200, nullable = false)
    private String lectLoc;

    @Column(name = "lect_status", length = 10, nullable = false)
    private String lectStatus;

    @Column(name = "studt_limit", nullable = false)
    private Integer studtLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUR_NO", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INST_NO", nullable = false)
    private Inst inst;
}