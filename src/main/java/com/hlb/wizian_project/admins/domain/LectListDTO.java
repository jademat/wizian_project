package com.hlb.wizian_project.admins.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LectListDTO {
    private int lectNo;
    private String lectNm;
    private String instNm;
    private LocalDateTime lectStart;
    private LocalDateTime lectSubmit;
    private String lectStatus;

}
