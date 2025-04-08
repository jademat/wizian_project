package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assign_submit")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignSubmit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int assignNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_info_no", nullable = false)
    private AssignInfo assignInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lect_no", nullable = false)
    private LectInfo lectInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_no", nullable = false)
    private LectApply lectApply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;

    @Column(nullable = false)
    private int assignPoint;

    @Column(length = 2000)
    private String assignAns;

    @Column(nullable = false)
    private LocalDateTime assignDuedate;
}