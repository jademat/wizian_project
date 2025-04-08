package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
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
    @Column(name = "notice_no", nullable = false)
    private Integer noticeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_NO", nullable = false)
    private Admin admin;

    @Column(name = "notice_cont", length = 1000, nullable = false)
    private String noticeCont;

    @Column(name = "notice_date", nullable = false)
    private LocalDateTime noticeDate;

    @Column(name = "notice_duedate", nullable = false)
    private LocalDateTime noticeDuedate;
}