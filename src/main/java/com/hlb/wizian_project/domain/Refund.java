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
    @Column(nullable = false)
    private int refundNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_no", nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private LocalDateTime refundDate;

    @Column(nullable = false)
    private int refundAmt;

    @Column(length = 10, nullable = false)
    private String refundState;

    @Column(length = 100)
    private String refundReason;
}