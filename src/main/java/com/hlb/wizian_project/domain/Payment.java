package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int payNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lect_no", nullable = false)
    private LectInfo lectInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_no", nullable = false)
    private LectApply lectApply;

    @Column(length = 8, nullable = false)
    private String payDate;

    @Column(nullable = false)
    private int payAmt;

    @Column(length = 20, nullable = false)
    private String payType;

    @Column(length = 10, nullable = false)
    private String payState;
}