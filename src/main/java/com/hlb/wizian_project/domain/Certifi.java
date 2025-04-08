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
    @Column(name = "csert_no", nullable = false)
    private Integer csertNo;

    @Column(name = "cert_date", nullable = false)
    private LocalDateTime certDate;

    @Column(name = "cert_file", length = 2000, nullable = false)
    private String certFile;

    @Column(name = "apply_no", nullable = false)
    private Integer applyNo;

    @Column(name = "stdnt_no", nullable = false)
    private Integer stdntNo;
}