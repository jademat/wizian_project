package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_content")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int boardContentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    private BoardInfo boardInfo;

    @Column(length = 100, nullable = false)
    private String boardTitle;

    @Column(length = 2000, nullable = false)
    private String boardCont;

    @Column(nullable = false)
    private int fileNo;

    @Column(length = 50, nullable = false)
    private String boardWriter;

    @Column(nullable = false)
    private int boardViews;

    @Column(nullable = false)
    private int boardSecu;

    @Column(nullable = false)
    private LocalDateTime boardDate;

    @Column(nullable = false)
    private int boardContentNoPre;
}