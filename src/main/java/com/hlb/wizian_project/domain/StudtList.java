package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "studt_list")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudtList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_no", nullable = false)
    private Integer listNo;

    @Column(name = "lect_no", nullable = false)
    private Integer lectNo;

    @Column(name = "stdnt_no", nullable = false)
    private Integer stdntNo;

    @Column(name = "attend_date", nullable = false)
    private LocalDateTime attendDate;

    @Column(name = "attend_status", nullable = false)
    private Integer attendStatus;
}