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
    @Column(name = "assign_no", nullable = false)
    private Integer assignNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_info_no", nullable = false)
    private AssignInfo assignInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lect_no", nullable = false)
    private LectInfo lectInfo;

    @Column(name = "assign_point", nullable = false)
    private Integer assignPoint;

    @Column(name = "assign_ans", length = 2000)
    private String assignAns;

    @Column(name = "assign_duedate")
    private LocalDateTime assignDuedate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_no", nullable = false)
    private LectApply apply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;
}