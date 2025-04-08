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
    @Column(nullable = false)
    private int stdntNo;

    @Column(length = 50, nullable = false)
    private String stdntId;

    @Column(length = 512, nullable = false)
    private String pwd;

    @Column(length = 50, nullable = false)
    private String stdntEmail;

    @Column(length = 50, nullable = false)
    private String stdntNm;

    @Column(length = 10)
    private String genCd;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 6)
    private String zipCd;

    @Column(length = 200)
    private String addr;

    @Column(length = 200)
    private String addrDtl;

    @Column(nullable = false)
    private LocalDateTime stdntRegdate;
}