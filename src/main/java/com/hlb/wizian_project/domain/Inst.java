package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inst")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inst_no", nullable = false)
    private Integer instNo;

    @Column(name = "inst_id", length = 50, nullable = false)
    private String instId;

    @Column(name = "passwd", length = 512, nullable = false)
    private String passwd;

    @Column(name = "inst_nm", length = 50, nullable = false)
    private String instNm;

    @Column(name = "gen_cd", length = 10)
    private String genCd;

    @Column(name = "birth_date", length = 8)
    private String birthDate;

    @Column(name = "zip_cd", length = 6)
    private String zipCd;

    @Column(name = "addr", length = 200)
    private String addr;

    @Column(name = "addr_dtl", length = 200)
    private String addrDtl;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "inst_date", nullable = false)
    private LocalDateTime instDate;
}