package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no", nullable = false)
    private Integer adminNo;

    @Column(name = "login_id", length = 50, nullable = false)
    private String loginId;

    @Column(name = "admin_nm", length = 50, nullable = false)
    private String adminNm;

    @Column(name = "pswd", length = 512, nullable = false)
    private String pswd;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "hp_no", length = 20, nullable = false)
    private String hpNo;

    @Column(name = "gen_cd", length = 10, nullable = false)
    private String genCd;

    @Column(name = "birth_date", length = 8, nullable = false)
    private String birthDate;

    @Column(name = "addr", length = 200, nullable = false)
    private String addr;

    @Column(name = "addr_dtl", length = 200, nullable = false)
    private String addrDtl;

    @Column(name = "addr_date", nullable = false)
    private LocalDateTime addrDate;
}