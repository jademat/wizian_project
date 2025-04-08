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
    @Column(name = "file_no", nullable = false)
    private Integer fileNo;

    @Column(name = "file_code", length = 10, nullable = false)
    private String fileCode;

    @Column(name = "uuid", length = 512, nullable = false)
    private String uuid;

    @Column(name = "file_nm", length = 50, nullable = false)
    private String fileNm;

    @Column(name = "file_size", length = 50, nullable = false)
    private String fileSize;

    @Column(name = "file_dirt", length = 100, nullable = false)
    private String fileDirt;
}