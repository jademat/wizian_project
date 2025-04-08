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
public class BoardCommt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int boardCommtNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_content_no", nullable = false)
    private BoardContent boardContent;

    @Column(length = 50, nullable = false)
    private String boardCommtWriter;

    @Column(length = 1000, nullable = false)
    private String boardCommtCont;

    @Column(nullable = false)
    private int fileNo;

    @Column(nullable = false)
    private LocalDateTime boardCommtDate;

    @Column(nullable = false)
    private int boardCommtNoPre;
}