package com.hlb.wizian_project.admins.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LectDTO {
    private int courNo;             // 과정 번호 (select box)
    private int instNo;             // 강사 번호 (select box)
    private String lectNm;          // 강의명
    private int lectPrice;          // 강의 금액
    private String lectDesc;        // 계획서
    private LocalDate lectStart;       // 개강일 (String → LocalDateTime 변환 예정)
    private LocalDate  lectSubmit;      // 수료일 (String → LocalDateTime 변환 예정)
    private String lectLoc;         // 학원 위치
    private int studtLimit;         // 수강 제한 인원
}
