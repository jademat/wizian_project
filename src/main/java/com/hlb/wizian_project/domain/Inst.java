package com.hlb.wizian_project.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "inst")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Inst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int instNo;

    @Column(length = 50, nullable = false)
    private String instId;

    @Column(length = 512, nullable = false)
    private String passwd;

    @Column(length = 50, nullable = false)
    private String instNm;

    @Column(length = 10)
    private String genCd;

    @Column(length = 8)
    private String birthDate;

    @Column(length = 6)
    private String zipCd;

    @Column(length = 200)
    private String addr;

    @Column(length = 200)
    private String addrDtl;

    @Column(length = 50)
    private String email;

    @CreationTimestamp
    private java.time.LocalDateTime instDate;
}