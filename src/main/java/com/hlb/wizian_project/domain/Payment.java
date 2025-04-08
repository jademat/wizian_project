package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_no", nullable = false)
    private Integer payNo;

    @Column(name = "lect_no")
    private Integer lectNo;

    @Column(name = "pay_date", length = 8)
    private String payDate;

    @Column(name = "pay_amt")
    private Integer payAmt;

    @Column(name = "pay_type")
    private Integer payType;

    @Column(name = "pay_state", length = 10)
    private String payState;

    @Column(name = "apply_no")
    private Integer applyNo;
}