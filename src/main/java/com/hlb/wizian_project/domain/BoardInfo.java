package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int boardNo;

    @Column(length = 50, nullable = false)
    private String boardCate;

    @Column(length = 10, nullable = false)
    private String boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lect_no")
    private LectInfo lectInfo;
}