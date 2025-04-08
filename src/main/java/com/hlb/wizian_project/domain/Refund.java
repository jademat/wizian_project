package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refund")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_no", nullable = false)
    private Integer refundNo;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    @Column(name = "refund_amt")
    private Integer refundAmt;

    @Column(name = "refund_state", length = 8)
    private String refundState;

    @Column(name = "refund_reason", length = 8)
    private String refundReason;

    @Column(name = "pay_no", nullable = false)
    private Integer payNo;
}