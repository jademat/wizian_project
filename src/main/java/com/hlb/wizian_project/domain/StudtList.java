package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "studt_list")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudtList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int attendNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lect_no", nullable = false)
    private LectInfo lectInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;

    @Column(nullable = false)
    private LocalDateTime attendDate;

    @Column(nullable = false)
    private int attendStatus;
}