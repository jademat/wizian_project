package com.hlb.wizian_project.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(nullable = false)
    private int adminNo;

    @Column(length = 50, nullable = false)
    private String loginId;

    @Column(length = 50, nullable = false)
    private String adminNm;

    @Column(length = 512, nullable = false)
    private String pswd;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String hpNo;

    @Column(length = 10)
    private String genCd;

    @Column(length = 8)
    private String birthDate;

    @Column(length = 200)
    private String addr;

    @Column(length = 200)
    private String addrDtl;

    @Column
    private String role;

    @CreationTimestamp
    private LocalDateTime addrDate;

    @Transient
    @JsonProperty("g-recaptcha-response")
    private String gRecaptchaResponse;
}