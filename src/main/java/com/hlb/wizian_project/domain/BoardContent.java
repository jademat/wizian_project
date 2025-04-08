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
    @Column(name = "board_content_no", nullable = false)
    private Integer boardContentNo;

    @Column(name = "board_no", nullable = false)
    private Integer boardNo;

    @Column(name = "board_title", length = 1000, nullable = false)
    private String boardTitle;

    @Column(name = "board_cont", length = 2000, nullable = false)
    private String boardCont;

    @Column(name = "file_no", nullable = false)
    private Integer fileNo;

    @Column(name = "board_writer", length = 50, nullable = false)
    private String boardWriter;

    @Column(name = "board_views", nullable = false)
    private Integer boardViews;

    @Column(name = "board_secu", nullable = false)
    private Integer boardSecu;

    @Column(name = "board_date", nullable = false)
    private LocalDateTime boardDate;

    @Column(name = "board_content_no_pre", nullable = false)
    private Integer boardContentNoPre;
}