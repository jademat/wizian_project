package com.hlb.wizian_project.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "file")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int fileNo;

    @Column(length = 10, nullable = false)
    private String fileCode;

    @Column(length = 100, nullable = false)
    private String uuid;

    @Column(length = 255, nullable = false)
    private String fileNm;

    @Column(length = 20, nullable = false)
    private String fileSize;

    @Column(length = 255, nullable = false)
    private String fileDirt;
}