package com.hlb.wizian_project.domain;

import javax.persistence.*;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assign_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_info_no", nullable = false)
    private Integer assignInfoNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECT_NO", nullable = false)
    private LectInfo lectInfo;

    @Column(name = "assign_info_nm", length = 50, nullable = false)
    private String assignInfoNm;

    @Column(name = "assign_info_year", length = 50, nullable = false)
    private String assignInfoYear;

    @Column(name = "assign_info_month", length = 50, nullable = false)
    private String assignInfoMonth;

    @Column(name = "inst_nm", length = 50, nullable = false)
    private String instNm;

    @Column(name = "assign_date", nullable = false)
    private LocalDateTime assignDate;

    @Column(name = "assign_duedate", nullable = false)
    private LocalDateTime assignDuedate;

    @Column(name = "assign_qnum", nullable = false)
    private Integer assignQnum;

    @Column(name = "assign_que", length = 1000, nullable = false)
    private String assignQue;

    @Column(name = "assign_corct", length = 2000, nullable = false)
    private String assignCorct;

    @Column(name = "assign_status", length = 10, nullable = false)
    private String assignStatus;
}