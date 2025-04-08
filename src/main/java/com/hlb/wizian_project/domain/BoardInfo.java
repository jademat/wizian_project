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
    @Column(name = "baord_no", nullable = false)
    private Integer baordNo;

    @Column(name = "board_cate", length = 50, nullable = false)
    private String boardCate;

    @Column(name = "board_type", length = 10, nullable = false)
    private String boardType;

    @Column(name = "lect_no", nullable = false)
    private Integer lectNo;
}