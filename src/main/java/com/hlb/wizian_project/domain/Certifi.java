package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certifi")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certifi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int certifiNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_no", nullable = false)
    private LectApply lectApply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private Studnt studnt;

    @Column(nullable = false)
    private LocalDateTime certDate;

    @Column(length = 255, nullable = false)
    private String certFile;
}