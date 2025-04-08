package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "studnt")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stdnt_no", nullable = false)
    private Integer stdntNo;

    @Column(name = "stdnt_id", length = 50, nullable = false)
    private String stdntId;

    @Column(name = "pwd", length = 512, nullable = false)
    private String pwd;

    @Column(name = "stdnt_email", length = 50, nullable = false)
    private String stdntEmail;

    @Column(name = "stdnt_nm", length = 50, nullable = false)
    private String stdntNm;

    @Column(name = "gen_cd", length = 10)
    private String genCd;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "zip_cd", length = 6)
    private String zipCd;

    @Column(name = "addr", length = 200)
    private String addr;

    @Column(name = "addr_dtl", length = 200)
    private String addrDtl;

    @Column(name = "stdnt_regdate", nullable = false)
    private LocalDateTime stdntRegdate;
}