package com.hlb.wizian_project.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "assign_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AssignInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int assignInfoNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lect_no", nullable = false)
    private LectInfo lectInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inst_no", nullable = false)
    private Inst inst;

    @Column(length = 50, nullable = false)
    private String assignInfoNm;

    @Column(length = 50, nullable = false)
    private String assignInfoYear;

    @Column(length = 50, nullable = false)
    private String assignInfoMonth;

    @Column(length = 50, nullable = false)
    private String instNm;

    @CreationTimestamp
    private LocalDateTime assignDate;

    @Column(nullable = false)
    private LocalDateTime assignDuedate;

    @Column(nullable = false)
    private int assignQnum;

    @Column(length = 1000, nullable = false)
    private String assignQue;

    @Column(length = 2000, nullable = false)
    private String assignCorct;

    @Column(length = 10, nullable = false)
    private String assignStatus;
}