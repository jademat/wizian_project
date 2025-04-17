package com.hlb.wizian_project.admins.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LectDetailDTO {
    private int lectNo;
    private String lectNm;
    private String lectDesc;
    private LocalDateTime lectStart;     // 수정
    private LocalDateTime lectSubmit;    // 수정
    private int lectPrice;
    private String lectLoc;
    private String lectStatus;
    private int studtLimit;
    private String instNm;
    private String courNm;
    private int weekTime;   // 주당 수업시간
    private int fullTime;   // 총수업시간
}
