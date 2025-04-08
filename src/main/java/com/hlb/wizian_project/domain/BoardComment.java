package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_commt")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_commt_no", nullable = false)
    private Integer boardCommtNo;

    @Column(name = "board_content_no", nullable = false)
    private Integer boardContentNo;

    @Column(name = "board_commt_writer", length = 50, nullable = false)
    private String boardCommtWriter;

    @Column(name = "board_commt_cont", length = 1000, nullable = false)
    private String boardCommtCont;

    @Column(name = "file_no", nullable = false)
    private Integer fileNo;

    @Column(name = "board_commt_date", nullable = false)
    private LocalDateTime boardCommtDate;

    @Column(name = "board_commt_no_pre", nullable = false)
    private Integer boardCommtNoPre;
}