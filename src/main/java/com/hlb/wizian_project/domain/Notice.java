package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int noticeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_no", nullable = false)
    private Admin admin;

    @Column(length = 1000, nullable = false)
    private String noticeCont;

    @CreationTimestamp
    private LocalDateTime noticeDate;

    @Column(nullable = false)
    private LocalDateTime noticeDuedate;
}